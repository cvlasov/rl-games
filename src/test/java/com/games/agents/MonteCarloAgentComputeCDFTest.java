package com.games.agents;

import static org.junit.Assert.assertEquals;

import com.games.general.Action;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(Parameterized.class)
public class MonteCarloAgentComputeCDFTest {

  private final MonteCarloAgent mc = new MonteCarloAgent(0.1);
  private final double DELTA = 0.00001;

  @Parameters
  public static Collection<Object[]> returns() {
     return Arrays.asList(new Object[][] {
        { Arrays.asList(new Action[] {}),
          new Action[] {},
          new double[] {},
          new double[] {} },

        { Arrays.asList(new Action[] { new TicTacToeAction(0, TokenType.X) }),
          new Action[] { new TicTacToeAction(0, TokenType.X) },
          new double[] { 1.0 },
          new double[] { 1.0 } },


        { Arrays.asList(new Action[] { new TicTacToeAction(0, TokenType.X),
                                       new TicTacToeAction(1, TokenType.O) }),
          new Action[] { new TicTacToeAction(0, TokenType.X),
                         new TicTacToeAction(1, TokenType.O) },
          new double[] { 0.2, 0.8 },
          new double[] { 0.2, 1.0 } },

        { Arrays.asList(new Action[] { new TicTacToeAction(0, TokenType.X),
                                       new TicTacToeAction(1, TokenType.O) }),
          new Action[] { new TicTacToeAction(1, TokenType.O),
                         new TicTacToeAction(0, TokenType.X) },
          new double[] { 0.2, 0.8 },
          new double[] { 0.8, 1.0 } },

        { Arrays.asList(new Action[] { new TicTacToeAction(0, TokenType.X),
                                       new TicTacToeAction(1, TokenType.O),
                                       new TicTacToeAction(2, TokenType.X),
                                       new TicTacToeAction(3, TokenType.O),
                                       new TicTacToeAction(4, TokenType.X) }),
          new Action[] { new TicTacToeAction(0, TokenType.X),
                         new TicTacToeAction(3, TokenType.O),
                         new TicTacToeAction(2, TokenType.X),
                         new TicTacToeAction(1, TokenType.O),
                         new TicTacToeAction(4, TokenType.X) },
          new double[] { 0.1, 0.5,  0.05, 0.25, 0.1 },
          new double[] { 0.1, 0.35, 0.4,  0.9,  1.0 } }
     });
  }

  @Parameter(0)
  public List<Action> actions;

  // Contains same actions as in parameter 0, but possibly in a different order
  @Parameter(1)
  public Action[] policyActions;

  @Parameter(2)
  public double[] probabilities;

  @Parameter(3)
  public double[] expectedCDF;

  @Test
  public void testComputeCDF() {
    Map<Action, Double> policy = new HashMap<>();

    for (int i = 0 ; i < actions.size() ; i++) {
      policy.put(policyActions[i], probabilities[i]);
    }

    double[] actualCDF = mc.computeCDF(actions, policy);

    for (int i = 0 ; i < expectedCDF.length ; i++) {
      assertEquals(
          String.format("[value at index %d]", i),
          expectedCDF[i], actualCDF[i], DELTA);
    }
  }
}
