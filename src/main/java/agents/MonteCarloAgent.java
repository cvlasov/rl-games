package agents;

import general.Action;
import general.Agent;
import general.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Agent that uses an epsilon-soft on-policy Monte Carlo control algorithm. */
public class MonteCarloAgent implements Agent {

  /** Value of epsilon used in policy improvement. */
  private final double epsilon;

  /** Agent's policy. */
  private final HashMap<State, HashMap<Action, Double>> PI = new HashMap<>();

  /** Action-value function for policy PI. */
  private final HashMap<State, HashMap<Action, Double>> Q = new HashMap<>();

  /**
   *  Returns for the first occurrences of all (state, action) pairs encountered
   *  in all episodes so far (including the one in progress).
   */
  private final HashMap<State, HashMap<Action, List<Integer>>> overallReturns =
          new HashMap<>();

  /** States (and actions taken from them) in THIS episode so far. */
  private final HashMap<State, List<Action>> episodeStates = new HashMap<>();

  /** Most recent state. */
  private State lastState;

  /** Most recent action, for which the return has not yet been given. */
  private Action lastAction;

  public MonteCarloAgent(double epsilon) {
    //System.out.println("Monte Carlo: agent just created");
    this.epsilon = epsilon;
  }

  @Override
  public void initializeBeforeNewGame() {
    //System.out.println("Monte Carlo: initializing before new game");
    episodeStates.clear();
    lastAction = null;
    lastState = null;
  }

  @Override
  public Action chooseAction(State state) {
    List<Action> actions = state.getActions();
    //System.out.println("Choosing action");

    if (!PI.containsKey(state)) {
      System.out.println("Monte Carlo: first time state is reached");

      // First time this state has been reached in any episode, so the policy
      // is arbitrary (i.e. all actions are equally likely to be chosen)
      int randomIndex = (int) (Math.random() * actions.size());
      //System.out.println("Monte Carlo: random action index is " + randomIndex);
      lastAction = actions.get(randomIndex);

    } else {
      System.out.println("Monte Carlo: state was reached before");

      // This state was encountered in a previous episode, so the policy is not
      // arbitrary
      HashMap<Action, Double> policy = PI.get(state);

      // Compute cumulative distribution function of policy at given state
      double[] cdf = new double[policy.size()];
      cdf[0] = policy.get(actions.get(0));

      for (int i = 1; i < cdf.length; i++) {
        cdf[i] = cdf[i - 1] + policy.get(actions.get(i));
      }

      int i;

      while (true) {
        i = Arrays.binarySearch(cdf, Math.random());

        if (i < 0) {
          // Arrays.binarySearch returns -i-1 if the insertion index is i (and the
          // value is not already in the array
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

    lastState = state;
    return lastAction;
  }

  /** Give return for most recent action (stored in {@link #lastAction}). */
  @Override
  public void giveReturn(int amount) {
    if (episodeStates.containsKey(lastState)
            && episodeStates.get(lastState).contains(lastAction)) {
      // Not the first time 'lastAction' was chosen at 'lastState' so no need
      // to record the given return.
      return;
    }

    if (!episodeStates.containsKey(lastState)) {
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

    // Record the return given for the first execution of lastAction at state
    // lastState in this episode.
    overallReturns.get(lastState).get(lastAction).add(amount);
  }

  @Override
  public void gameOver() {
    System.out.println("Monte Carlo: GAME OVER");
    // POLICY EVALUATION
    for (State s : overallReturns.keySet()) {
      for (Action a : overallReturns.get(s).keySet()) {
        List<Integer> returns = overallReturns.get(s).get(a);
        double average = 0.0;

        for (int i : returns) {
          average += i;
        }

        average /= returns.size();

        if (!Q.containsKey(s)) {
          Q.put(s, new HashMap<>());
        }

        Q.get(s).put(a, average);
      }
    }

    // POLICY IMPROVEMENT
    for (State s : episodeStates.keySet()) {
      Action bestAction = null;
      double bestValue = Double.MIN_VALUE;

      for (Action a : episodeStates.get(s)) {
        double value = Q.get(s).get(a);

        if (value > bestValue) {
          bestValue = value;
          bestAction = a;
        }
      }

      if (!PI.containsKey(s)) {
        PI.put(s, new HashMap<>());
      }

      int numActions = s.getActions().size();

      for (Action a : s.getActions()) {
        if (a.equals(bestAction)) {
          PI.get(s).put(a, 1 - epsilon + (epsilon/numActions));
        } else {
          PI.get(s).put(a, epsilon/numActions);
        }
      }
    }
  }
}
