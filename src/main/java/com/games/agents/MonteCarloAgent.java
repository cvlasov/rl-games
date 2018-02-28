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
  private final double epsilon;

  /**
   * Agent's policy.
   * <p>
   * Maps a state to all possible actions from that state and maps each of these
   * actions to the probability of choosing that action from that state under
   * this policy.
   */
  private final HashMap<State, HashMap<Action, Double>> PI = new HashMap<>();

  /**
   * Action-value function for policy PI.
   * <p>
   * Maps a state to all actions taken from this state in all episodes so far
   * and maps each of these actions to the expected return from starting at the
   * state, executing the action, and then following current policy.
   */
  private final HashMap<State, HashMap<Action, Double>> Q = new HashMap<>();

  /**
   *  Returns for the first occurrences of all (state, action) pairs encountered
   *  in all episodes so far (including the one in progress).
   */
  private final HashMap<State, HashMap<Action, List<Double>>> overallReturns =
          new HashMap<>();

  /**
   * Maps each state encountered in the current episode so far to all actions
   * taken from that state in the current episode so far.
   */
  private final HashMap<State, List<Action>> episodeStates = new HashMap<>();

  /**
   * Most recent state from which an action was chosen and for which a return
   * has not yet been given.
   */
  private State lastState;

  /** Most recent action for which a return has not yet been given. */
  private Action lastAction;

  public MonteCarloAgent(double epsilon) {
    this.epsilon = epsilon;
  }

  @VisibleForTesting
  MonteCarloAgent(double epsilon, Map<State, List<Action>> episodeStates) {
    this.epsilon = epsilon;
    this.episodeStates.clear();
    this.episodeStates.putAll(episodeStates);
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
      if (debug) System.out.println("first time state was ever encountered\n\n");

      // First time this state has been reached in any episode, so the policy
      // is arbitrary (i.e. all actions are equally likely to be chosen)
      int randomIndex = (int) (Math.random() * actions.size());
      lastAction = actions.get(randomIndex);

    } else {
      // This state was encountered in a previous episode, so the policy is not
      // arbitrary
      HashMap<Action, Double> policy = PI.get(state);

      // System.out.println("available actions:");
      // for (Action a : actions) {
      //   a.print(); System.out.println();
      // }
      //
      // System.out.println("policy actions:");
      // for (Action a : policy.keySet()) {
      //   a.print(); System.out.println();
      // }

      // System.out.println("policy size (" + policy.size()
      //                    + "), actions from this state (" + actions.size() + ")");

      if (debug) System.out.println("state encountered in previous episode - "
                         + "policy.keySet().equals(actions)? "
                         + policy.keySet().equals(new HashSet(actions))
                         + "\n\n");

      // Compute cumulative distribution function of policy at given state
      double[] cdf = new double[policy.size()];
      cdf[0] = policy.get(actions.get(0));

      for (int i = 1; i < cdf.length /* = policy.size() = actions.size() */; i++) {
        cdf[i] = cdf[i - 1] + policy.get(actions.get(i));
      }

      int i;

      while (true) {
        i = Arrays.binarySearch(cdf, Math.random());

        if (i < 0) {
          // Arrays.binarySearch returns -i-1 if the insertion index is i (and
          // the value is not already in the array
          i = -i - 1;
          break;

        } else if (i < cdf.length) {
          break;
        }
        // Redo if Arrays.binarySearch returns cdf.length (due to error in
        // rounding of double value)
      }

      lastAction = actions.get(i);
    }

    lastState = state.copy();
    return lastAction;
  }

  /** Handles the return received for {@link #lastAction}. */
  @Override
  public void receiveReturn(double amount) {
    if (episodeStates.containsKey(lastState)
            && episodeStates.get(lastState).contains(lastAction)) {
      // Not the first time 'lastAction' was chosen at 'lastState' so no need
      // to record the given return.
      return;
    }

    if (!episodeStates.containsKey(lastState)) {
      // First time 'lastState' was visited in this episode
      episodeStates.put(lastState, new ArrayList<>());
    }

    // Record that the pair (lastState, lastAction) was encountered (for the
    // first time in this episode)
    episodeStates.get(lastState).add(lastAction);

    if (!overallReturns.containsKey(lastState)) {
      overallReturns.put(lastState, new HashMap<>());
    }

    if (!overallReturns.get(lastState).containsKey(lastAction)) {
      overallReturns.get(lastState).put(lastAction, new ArrayList<>());
    }

    // Record the return given for the first execution of 'lastAction' at
    // 'lastState' in this episode.
    overallReturns.get(lastState).get(lastAction).add(amount);
  }

  @Override
  public void gameOver() {
    policyEvaluation();
    policyImprovement();
  }

  @VisibleForTesting
  HashMap<State, List<Action>> getEpisodeStates() {
    return episodeStates;
  }

  // Recalculate action-value function for states that were visited in the most
  // recent episode.
  private void policyEvaluation() {
    for (State s : episodeStates.keySet()) {
      for (Action a : episodeStates.get(s)) {
        List<Double> returns = overallReturns.get(s).get(a);
        double average = 0.0;

        for (double i : returns) {
          average += i;
        }

        average /= returns.size();

        if (!Q.containsKey(s)) {
          Q.put(s, new HashMap<>());
        }

        Q.get(s).put(a, average);
      }
    }
  }

  private void policyImprovement() {
    if (debug) System.out.println("*****************************");
    if (debug) System.out.println("POLICY IMPROVEMENT");

    for (State s : episodeStates.keySet()) {
      if (debug) System.out.println("\nupdating policy for state");
      if (debug) s.print();

      Action bestAction = null;
      double bestValue = -Double.MAX_VALUE;

      for (Action a : Q.get(s).keySet()) {
        double value = Q.get(s).get(a);

        if (value > bestValue) {
          bestValue = value;
          bestAction = a;
        }
      }

      if (!PI.containsKey(s)) {
        if (debug) System.out.println("adding it to the policy for the first time!");
        PI.put(s, new HashMap<>());
      }

      double randomProb = epsilon / s.getActions().size();

      for (Action a : s.getActions()) {
        if (debug) a.print(); if (debug) System.out.println();

        if (a.equals(bestAction)) {
          PI.get(s).put(a, 1 - epsilon + randomProb);
        } else {
          PI.get(s).put(a, randomProb);
        }
      }
    }

    if (debug) System.out.println("*****************************\n");
  }
}
