package com.games.agents;

import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper;

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
public class MonteCarloAgentUpdatePolicyTest {

  @Parameters
  public static Collection<Object[]> returns() {
     return Arrays.asList(new Object[][] {
       { 0.99,
         new TicTacToeAction(0, TicTacToeHelper.TokenType.X),
         new Action[] { new TicTacToeAction(0, TicTacToeHelper.TokenType.X) },
         new double[] { 1.0 } },

       { 0.4,
         new TicTacToeAction(2, TicTacToeHelper.TokenType.X),
         new Action[] { new TicTacToeAction(0, TicTacToeHelper.TokenType.X),
                        new TicTacToeAction(1, TicTacToeHelper.TokenType.O),
                        new TicTacToeAction(2, TicTacToeHelper.TokenType.X),
                        new TicTacToeAction(3, TicTacToeHelper.TokenType.O) },
         new double[] { 0.1, 0.1, 0.7, 0.1 } },
     });
  }

  @Parameter(0)
  public Double epsilon;

  @Parameter(1)
  public Action bestAction;

  @Parameter(2)
  public Action[] availableActions;

  @Parameter(3)
  public double[] expectedProbabilities;

  @Test
  public void testUpdatePolicyForNewState() {
    State mockState = mock(State.class);
    when(mockState.getActions()).thenReturn(Arrays.asList(availableActions));

    MonteCarloAgent mc = new MonteCarloAgent(epsilon);
    mc.updatePolicyForState(mockState, bestAction);

    assertThat(mc.getPolicy(), hasKey(mockState));
    checkCorrectPolicy(mc, mockState);
  }

  @Test
  public void testUpdatePolicyForRepeatedState() {
    State mockState = mock(State.class);
    when(mockState.getActions()).thenReturn(Arrays.asList(availableActions));

    Map<Action, Double> probabilityMap = new HashMap<>();

    for (int i = 0 ; i < availableActions.length ; i++) {
      probabilityMap.put(availableActions[i], expectedProbabilities[i] - 0.1);
    }

    Map<State, Map<Action, Double>> policy = new HashMap<>();
    policy.put(mockState, probabilityMap);

    MonteCarloAgent mc = new MonteCarloAgent(epsilon, policy, false, true);
    mc.updatePolicyForState(mockState, bestAction);

    checkCorrectPolicy(mc, mockState);
  }

  private void checkCorrectPolicy(
      MonteCarloAgent mc, State s) {
    assertThat(mc.getPolicy(), hasKey(s));

    Map<Action, Double> probabilityMap = mc.getPolicy().get(s);
    assertThat(probabilityMap.keySet(), containsInAnyOrder(availableActions));

    for (int i = 0 ; i < availableActions.length ; i++) {
      assertThat(probabilityMap.get(availableActions[i]),
                 equalTo(expectedProbabilities[i]));
    }
  }
}
