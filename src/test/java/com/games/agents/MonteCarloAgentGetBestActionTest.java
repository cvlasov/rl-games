package com.games.agents;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import com.games.chungtoi.ChungToiState;
import com.games.chungtoi.ChungToiHelper;
import com.games.chungtoi.ChungToiPutAction;
import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper;
import com.games.tictactoe.TicTacToeNormalState;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(Parameterized.class)
public class MonteCarloAgentGetBestActionTest {

  private final double EPSILON = 0.1;

  @Parameters
  public static Collection<Object[]> returns() {
     return Arrays.asList(new Object[][] {
        { new TicTacToeNormalState(),
          new Action[] { new TicTacToeAction(0, TicTacToeHelper.TokenType.X),
                         new TicTacToeAction(1, TicTacToeHelper.TokenType.O),
                         new TicTacToeAction(2, TicTacToeHelper.TokenType.X), },
          new double[] { 0.0, -1.0, 1.0 },
          new Action[] { new TicTacToeAction(2, TicTacToeHelper.TokenType.X) } },

        { new ChungToiState(),
          new Action[] { new ChungToiPutAction(ChungToiHelper.TokenType.X_NORMAL, 0),
                         new ChungToiPutAction(ChungToiHelper.TokenType.O_NORMAL, 1),
                         new ChungToiPutAction(ChungToiHelper.TokenType.X_NORMAL, 2) },
          new double[] { 1.0, 0.0, 0.0 },
          new Action[] { new ChungToiPutAction(ChungToiHelper.TokenType.X_NORMAL, 0) } },

        { new TicTacToeNormalState(),
          new Action[] { new TicTacToeAction(0, TicTacToeHelper.TokenType.X),
                         new TicTacToeAction(1, TicTacToeHelper.TokenType.O),
                         new TicTacToeAction(2, TicTacToeHelper.TokenType.X), },
          new double[] { 0.0, 1.0, 1.0 },
          new Action[] { new TicTacToeAction(1, TicTacToeHelper.TokenType.O),
                         new TicTacToeAction(2, TicTacToeHelper.TokenType.X) } },

        { new ChungToiState(),
          new Action[] { new ChungToiPutAction(ChungToiHelper.TokenType.X_NORMAL, 0),
                         new ChungToiPutAction(ChungToiHelper.TokenType.O_NORMAL, 1),
                         new ChungToiPutAction(ChungToiHelper.TokenType.X_NORMAL, 2) },
          new double[] { 1.0, 1.0, 0.0 },
          new Action[] { new ChungToiPutAction(ChungToiHelper.TokenType.X_NORMAL, 0),
                         new ChungToiPutAction(ChungToiHelper.TokenType.O_NORMAL, 1) } },
     });
  }

  @Parameter(0)
  public State state;

  @Parameter(1)
  public Action[] actions;

  @Parameter(2)
  public double[] values;

  @Parameter(3)
  public Action[] expectedBestActions;

  @Test
  public void testGetBestAction() {
    Map<Action, Double> actionValuePairs = new HashMap<>();

    for (int i = 0 ; i < actions.length ; i++) {
      actionValuePairs.put(actions[i], values[i]);
    }

    Map<State, Map<Action, Double>> Q = new HashMap<>();
    Q.put(state, actionValuePairs);

    MonteCarloAgent mc = new MonteCarloAgent(EPSILON, Q, true, false);
    assertThat(Arrays.asList(expectedBestActions), hasItem(mc.getBestAction(state)));
  }
}
