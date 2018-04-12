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
 * Game-playing agent that uses an epsilon-soft on-policy Monte Carlo control
 * algorithm.
 */
public class MonteCarloAgent implements Agent {

  boolean debug = false;

  /** Value of epsilon used in policy improvement. */
  private final double EPSILON;

  /**
   * Agent's policy.
   * <p>
   * Maps a state to all possible actions from that state and maps each of these
   * actions to the probability of choosing that action from that state under
   * this policy.
   */
  private final Map<State, Map<Action, Double>> PI = new HashMap<>();

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

  public MonteCarloAgent(double e) {
    EPSILON = e;
  }

  public MonteCarloAgent(double e, boolean debug) {
    EPSILON = e;
    this.debug = debug;
  }

  @VisibleForTesting
  MonteCarloAgent(double e,
                  Map<State, Map<Action, Double>> episodeStates,
                  Map<State, Map<Action, Double>> actionValueFunction,
                  Map<State, Map<Action, Integer>> stateActionCounts) {
    EPSILON = e;
    this.episodeStates.clear();
    this.episodeStates.putAll(episodeStates);
    this.Q.clear();
    this.Q.putAll(actionValueFunction);
    this.stateActionCounts.clear();
    this.stateActionCounts.putAll(stateActionCounts);
  }

  @VisibleForTesting
  MonteCarloAgent(double e,
                  Map<State, Map<Action, Double>> map,
                  boolean isActionValueFunction,
                  boolean isPolicy) {
    EPSILON = e;

    if (isActionValueFunction) {
      this.Q.clear();
      this.Q.putAll(map);
    }

    if (isPolicy) {
      this.PI.clear();
      this.PI.putAll(map);
    }
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
      Map<Action, Double> policy = PI.get(state);

      assert (new HashSet<Action>(actions)).equals(policy.keySet())
             : "state's available actions not equal to actions in policy";

      if (debug) {
        state.print();
        System.out.println();

        System.out.println("available actions:");
        for (Action a : actions) {
          System.out.print("-- ");
          a.print();
          System.out.println();
        }

        System.out.println();

        System.out.println("policy actions:");
        for (Action a : policy.keySet()) {
          System.out.print("-- ");
          a.print();
          System.out.println();
        }

        System.out.println();
        System.out.println("state has been encountered before, computing CDF:");
      }

      double[] cdf = computeCDF(actions, policy);
      lastAction = actions.get(chooseActionIndex(cdf));
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
  public Map<State, Map<Action, Double>> getPolicy() {
    return PI;
  }

  /**
   * Computes the cumulative distribution function of the agent's policy at a
   * particular state.
   */
  @VisibleForTesting
  double[] computeCDF(List<Action> actions, Map<Action, Double> policy) {
    double[] cdf = new double[policy.size()];

    if (policy.size() == 0) return cdf;

    if (debug) {
      System.out.print("action ");
      actions.get(0).print();
      System.out.println(" has probability " + policy.get(actions.get(0)));
    }

    cdf[0] = policy.get(actions.get(0));

    for (int i = 1; i < actions.size() /* = policy.size() */ ; i++) {

      if (debug) {
        System.out.print("action ");
        actions.get(i).print();
        System.out.println(" has probability " + policy.get(actions.get(i)));
      }

      cdf[i] = cdf[i-1] + policy.get(actions.get(i));
    }

    return cdf;
  }

  private int chooseActionIndex(double[] cdf) {
    int i = Arrays.binarySearch(cdf, Math.random());

    if (i < 0) {
      // Arrays.binarySearch returns -i-1 if the insertion index is i (and
      // the value is not already in the array
      return (-i) - 1;

    } else if (i < cdf.length) {
      return i;

    } else { // i == cdf.length
      return cdf.length - 1;
    }
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

      updatePolicyForState(s, getBestAction(s));
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

  @VisibleForTesting
  void updatePolicyForState(State s, Action bestAction) {
    if (!PI.containsKey(s)) {
      PI.put(s, new HashMap<>());
    }

    // Probability for sub-optimal action
    double randomProb = EPSILON / s.getActions().size();

    for (Action a : s.getActions()) {
      if (a.equals(bestAction)) {
        PI.get(s).put(a, 1 - EPSILON + randomProb);
      } else {  // not the best action
        PI.get(s).put(a, randomProb);
      }
    }
  }
}
