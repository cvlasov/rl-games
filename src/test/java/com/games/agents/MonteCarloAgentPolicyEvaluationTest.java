package com.games.agents;

import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper;
import com.games.tictactoe.TicTacToeNormalState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.junit.Test;

public class MonteCarloAgentPolicyEvaluationTest {

  @Test
  public void testPolicyEvaluationForTicTacToeActionThatOccurredOnlyOnce() {
    testPolicyEvaluationForTicTacToeAction(1.0, 1.0);
  }

  @Test
  public void testPolicyEvaluationForTicTacToeActionThatOccurredManyTimes() {
    testPolicyEvaluationForTicTacToeAction(1.0, 0.0, 2.0, 1.0);
  }

  private void testPolicyEvaluationForTicTacToeAction(
      Double expectedAverage,
      Double... returns) {

    State state = new TicTacToeNormalState();

    Action action = new TicTacToeAction(0, TicTacToeHelper.TokenType.X);
    List<Action> actions = new ArrayList<>(); actions.add(action);

    List<Double> returnsList = new ArrayList<Double>(Arrays.asList(returns));

    HashMap<Action, List<Double>> returnsMap = new HashMap<>();
    returnsMap.put(action, returnsList);

    Map<State, List<Action>> episodeStates = new HashMap<>();
    episodeStates.put(state, actions);

    Map<State, HashMap<Action, List<Double>>> overallReturns = new HashMap<>();
    overallReturns.put(state, returnsMap);

    MonteCarloAgent mc = new MonteCarloAgent(0.1, episodeStates, overallReturns);
    mc.policyEvaluation();

    assertThat(mc.getActionValueFunction(), hasKey(state));
    assertThat(mc.getActionValueFunction().get(state), hasKey(action));
    assertThat(mc.getActionValueFunction().get(state).get(action),
               equalTo(expectedAverage));
  }
}
