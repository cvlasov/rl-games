package com.games.agents;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.Assert.assertThat;

import com.games.agents.MonteCarloAgent;
import com.games.chungtoi.ChungToiState;
import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeNormalState;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(Parameterized.class)
public class MonteCarloAgentOverallReturnsTest {

  @Parameters
  public static Collection<Object[]> returns() {
     return Arrays.asList(new Object[][] {
        { new ChungToiState(),        true },
        { new ChungToiState(),        false },
        { new TicTacToeNormalState(), true },
        { new TicTacToeNormalState(), false }
     });
  }

  @Parameter(0)
  public State startState;

  @Parameter(1)
  public Boolean sameAction;  // not used by all tests

  @Test
  public void testOverallReturnsContainsAllStatesEncountered() {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    State state1 = startState;
    Action action1 = mc.chooseAction(state1);
    mc.receiveReturn(0.0);

    State state2 = state1.applyAction(action1);
    Action action2 = mc.chooseAction(state2);
    mc.receiveReturn(0.0);

    State state3 = state2.applyAction(action2);
    Action action3 = mc.chooseAction(state3);
    mc.receiveReturn(0.0);

    List<State> expectedStates = new ArrayList<>();
    expectedStates.add(state1);
    expectedStates.add(state2);
    expectedStates.add(state3);

    for (State expectedState : expectedStates) {
      assertThat(mc.getOverallReturns(), hasKey(expectedState));
    }
  }

  @Test
  public void testNewActionFromNewStateAddedToOverallReturns() {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();
    State state = startState;
    Action action = mc.chooseAction(state);
    double returnAmount = (int) (Math.random()*1000 + 1);
    mc.receiveReturn(returnAmount);

    assertThat(mc.getOverallReturns().get(state), hasKey(action));
    assertThat(mc.getOverallReturns().get(state).get(action),
               contains(returnAmount));
  }

  @Test
  public void testActionFromSameState() {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    State state = startState;
    Action originalAction = mc.chooseAction(state);
    double originalReturn = (int) (Math.random()*1000 + 1);
    mc.receiveReturn(originalReturn);

    Action newAction = mc.chooseAction(state);

    if (sameAction) {
      // Choose same action
      while (!newAction.equals(originalAction)) {
        newAction = mc.chooseAction(state);
      }
    } else {
      // Choose different action
      while (newAction.equals(originalAction)) {
        newAction = mc.chooseAction(state);
      }
    }

    double newReturn = originalReturn + 1;
    mc.receiveReturn(newReturn); // +1 to make the two returns different

    assertThat(mc.getOverallReturns(), hasKey(state));
    assertThat(mc.getOverallReturns().get(state), hasKey(newAction));

    if (sameAction) {
      assertThat(mc.getOverallReturns().get(state).get(newAction),
                 contains(originalReturn) /* NOT the new return as well */);
    } else {
      assertThat(mc.getOverallReturns().get(state).get(newAction),
                 contains(newReturn));
    }
  }

  @Test
  public void testActionFromPreviousGameAddedToOverallReturns() {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    State state = startState;
    Action previousGameAction = mc.chooseAction(state);
    double previousGameReturn = (int) (Math.random()*1000 + 1);
    mc.receiveReturn(previousGameReturn);
    mc.gameOver();
    mc.initializeBeforeNewGame();

    Action newAction = mc.chooseAction(state);

    // Choose the same action
    while (!newAction.equals(previousGameAction)) {
      newAction = mc.chooseAction(state);
    }

    double newReturn = previousGameReturn + 1;
    mc.receiveReturn(newReturn); // +1 to make the two returns different

    assertThat(mc.getOverallReturns(), hasKey(state));
    assertThat(mc.getOverallReturns().get(state), hasKey(newAction));
    assertThat(mc.getOverallReturns().get(state).get(newAction),
               contains(previousGameReturn, newReturn) /* in this order */);
  }
}
