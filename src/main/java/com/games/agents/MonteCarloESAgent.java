package com.games.agents;

import com.games.general.Action;
import com.games.general.Agent;
import com.games.general.State;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Game-playing agent that uses a Monte Carlo control algorithm assuming
 * exploring starts.
 */
public class MonteCarloESAgent implements Agent {

  boolean debug = false;

  /**
   * Agent's policy.
   * <p>
   * Maps a state to the best action found so far from that state.
   */
  private final Map<State, Action> PI = new HashMap<>();

  /**
   * Action-value function for policy PI.
   * <p>
   * Maps a state to all actions taken from this state in all episodes so far
   * and maps each of these actions to the expected return from starting at the
   * state, executing the action, and then following current policy.
   */
  private final Map<State, Map<Action, Double>> Q = new HashMap<>();

  /**
   *  Returns for the number of times each (state, action) pair has been
   *  encountered in all episodes so far (including the one in progress).
   */
  private final Map<State, Map<Action, Integer>> stateActionCounts =
          new HashMap<>();

  /**
   * Maps each state encountered in the current episode so far to all actions
   * taken from that state and their returns in the current episode so far.
   */
  private final Map<State, Map<Action, Double>> episodeStates = new HashMap<>();

  /**
   * Most recent state from which an action was chosen and for which a return
   * has not yet been given.
   */
  private State lastState;

  /** Most recent action for which a return has not yet been given. */
  private Action lastAction;

  public MonteCarloESAgent(boolean debug) {
    this.debug = debug;
  }

  @Override
  public String getName() {
    return "MonteC";
  }

  @Override
  public void initializeBeforeNewGame() {
    episodeStates.clear();
    lastAction = null;
    lastState = null;
  }

  @Override
  public Action chooseAction(State state) {
    List<Action> actions = state.getActions();

    if (!PI.containsKey(state)) {
      // First time this state has been reached in any episode, so the policy
      // is arbitrary (i.e. all actions are equally likely to be chosen)
      int randomIndex = (int) (Math.random() * actions.size());
      lastAction = actions.get(randomIndex);

      if (debug) {
        state.print();
        System.out.println("first time this state has been encountered");
      }

    } else {
      // This state was encountered in a previous episode, so the policy is not
      // arbitrary
      if (debug) {
        state.print();
        System.out.println("state has been encountered before");
      }
      lastAction = PI.get(state);
    }

    if (debug) {
      System.out.print("--> CHOSEN ACTION: ");
      lastAction.print();
      System.out.println();
      System.out.println();
    }

    lastState = state;
    return lastAction;
  }

  @Override
  public Action chooseActionES(State state, Action action) {
    lastAction = action;

    if (debug) {
      state.print();
      System.out.print("--> CHOSEN ACTION: ");
      lastAction.print();
      System.out.println();
      System.out.println();
    }

    lastState = state;
    return lastAction;
  }

  /** Handles the return received for {@link #lastAction}. */
  @Override
  public void receiveReturn(double amount) {
    if (episodeStates.containsKey(lastState)
            && episodeStates.get(lastState).containsKey(lastAction)) {
      // Not the first time 'lastAction' was chosen at 'lastState' so no need
      // to record the given return.
      return;
    }

    if (!episodeStates.containsKey(lastState)) {
      // First time 'lastState' was visited in this episode
      episodeStates.put(lastState, new HashMap<>());
    }

    // Record that the pair (lastState, lastAction) was encountered (for the
    // first time in this episode)
    episodeStates.get(lastState).put(lastAction, amount);

    if (!stateActionCounts.containsKey(lastState)) {
      stateActionCounts.put(lastState, new HashMap<>());
    }

    if (!stateActionCounts.get(lastState).containsKey(lastAction)) {
      stateActionCounts.get(lastState).put(lastAction, 0);
    }
  }

  @Override
  public void gameOver() {
    policyEvaluation();
    policyImprovement();
  }

  @VisibleForTesting
  Map<State, Map<Action, Double>> getEpisodeStates() {
    return episodeStates;
  }

  @VisibleForTesting
  Map<State, Map<Action, Integer>> getStateActionCounts() {
    return stateActionCounts;
  }

  @VisibleForTesting
  Map<State, Map<Action, Double>> getActionValueFunction() {
    return Q;
  }

  @VisibleForTesting
  public Map<State, Action> getPolicy() {
    return PI;
  }

  // Recalculate action-value function for states that were visited in the most
  // recent episode.
  @VisibleForTesting
  void policyEvaluation() {
    for (State s : episodeStates.keySet()) {
      for (Action a : episodeStates.get(s).keySet()) {
        int count = stateActionCounts.get(s).get(a);
        double returnSum = 0.0;

        if (count != 0) {
          returnSum = count * Q.get(s).get(a);

        } else if (!Q.containsKey(s)) {
          Q.put(s, new HashMap<>());
        }

        returnSum += episodeStates.get(s).get(a);
        double newAverage = returnSum / (count + 1);

        Q.get(s).put(a, newAverage);
        stateActionCounts.get(s).put(a, count + 1);
      }
    }
  }

  private void policyImprovement() {
    for (State s : episodeStates.keySet()) {
      // If there are no possible actions, then there is nothing to update
      if (s.getActions().size() == 0) {
        continue;
      }

      PI.put(s, getBestAction(s));
    }
  }

  @VisibleForTesting
  Action getBestAction(State s) {
    Action bestAction = null;
    double bestValue = -Double.MAX_VALUE;

    for (Action a : Q.get(s).keySet()) {
      double value = Q.get(s).get(a);

      if (value > bestValue) {
        bestValue = value;
        bestAction = a;
      }
    }

    return bestAction;
  }
}
