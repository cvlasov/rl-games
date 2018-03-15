package com.games.agents;

import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.agents.MonteCarloAgent;
import com.games.chungtoi.ChungToiState;
import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeNormalState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

@RunWith(Parameterized.class)
public class MonteCarloAgentStateActionCountsTest {

  private final double EPSILON = 0.1;
  private final double RETURN_AMOUNT = 0.0;

  private MonteCarloAgent mc;
  private Action action;

  @Parameters
  public static Collection<Object[]> returns() {
     return Arrays.asList(new Object[][] {
        { new ChungToiState() },
        { new TicTacToeNormalState() }
     });
  }

  @Parameter(0)
  public State startState;

  @Before
  public void setUp() {
    mc = new MonteCarloAgent(EPSILON);
    mc.initializeBeforeNewGame();

    action = mc.chooseAction(startState);
    mc.receiveReturn(RETURN_AMOUNT);
  }

  @Test
  public void testStateActionCountsContainsAllStatesEncountered() {
    State state2 = startState.applyAction(action);
    Action action2 = mc.chooseAction(state2);
    mc.receiveReturn(RETURN_AMOUNT);

    State state3 = state2.applyAction(action2);
    Action action3 = mc.chooseAction(state3);
    mc.receiveReturn(RETURN_AMOUNT);

    List<State> expectedStates = new ArrayList<>();
    expectedStates.add(startState);
    expectedStates.add(state2);
    expectedStates.add(state3);

    for (State expectedState : expectedStates) {
      assertThat(mc.getStateActionCounts(), hasKey(expectedState));
    }
  }

  @Test
  public void testNewActionHasZeroCountInStateActionCountsBeforeGameOver() {
    assertThat(mc.getStateActionCounts().get(startState), hasKey(action));
    assertThat(mc.getStateActionCounts().get(startState).get(action),
               equalTo(0 /* expected count */));
  }

  @Test
  public void testRepeatedActionHasSameCountInStateActionCountsBeforeGameOver() {
    int originalCount = mc.getStateActionCounts().get(startState).get(action);
    Action newAction = mc.chooseAction(startState);

    // Choose same action
    while (!newAction.equals(action)) {
      newAction = mc.chooseAction(startState);
    }

    mc.receiveReturn(RETURN_AMOUNT);

    assertThat(mc.getStateActionCounts().get(startState), hasKey(newAction));
    assertThat(mc.getStateActionCounts().get(startState).get(newAction),
               equalTo(originalCount));
  }

  @Test
  public void testGameOverIncrementsStateActionCounts() {
    int originalCount = mc.getStateActionCounts().get(startState).get(action);
    mc.gameOver();

    assertThat(mc.getStateActionCounts().get(startState), hasKey(action));
    assertThat(mc.getStateActionCounts().get(startState).get(action),
               equalTo(originalCount + 1));
  }
}
