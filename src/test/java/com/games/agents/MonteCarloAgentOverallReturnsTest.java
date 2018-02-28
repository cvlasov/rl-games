package com.games.agents;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.Assert.assertThat;

import com.games.agents.MonteCarloAgent;
import com.games.chungtoi.ChungToiState;
import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeNormalState;
import com.games.tictactoe.TicTacToeState;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MonteCarloAgentOverallReturnsTest {

  private enum Game {
    CHUNG_TOI,
    TIC_TAC_TOE
  }

  public MonteCarloAgentOverallReturnsTest() {}

  @Test
  public void testOverallReturnsContainsAllChungToiStatesEncountered() {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    ChungToiState state1 = new ChungToiState();
    Action action1 = mc.chooseAction(state1);
    mc.receiveReturn(0.0);

    ChungToiState state2 = (ChungToiState) state1.applyAction(action1);
    Action action2 = mc.chooseAction(state2);
    mc.receiveReturn(0.0);

    ChungToiState state3 = (ChungToiState) state2.applyAction(action2);
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
  public void testOverallReturnsContainsAllTicTacToeStatesEncountered() {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    TicTacToeState state1 = new TicTacToeNormalState();
    Action action1 = mc.chooseAction(state1);
    mc.receiveReturn(0.0);

    TicTacToeState state2 = (TicTacToeState) state1.applyAction(action1);
    Action action2 = mc.chooseAction(state2);
    mc.receiveReturn(0.0);

    TicTacToeState state3 = (TicTacToeState) state2.applyAction(action2);
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
  public void testNewActionFromNewChungToiStateAddedToOverallReturns() {
    testNewActionFromNewStateAddedToOverallReturns(Game.CHUNG_TOI);
  }

  @Test
  public void testNewActionFromNewTicTacToeStateAddedToOverallReturns() {
    testNewActionFromNewStateAddedToOverallReturns(Game.TIC_TAC_TOE);
  }

  private void testNewActionFromNewStateAddedToOverallReturns(Game game) {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    State state = null;

    switch (game) {
      case CHUNG_TOI:   state = new ChungToiState(); break;
      case TIC_TAC_TOE: state = new TicTacToeNormalState(); break;
    }

    Action action = mc.chooseAction(state);
    double returnAmount = (int) (Math.random()*1000 + 1);
    mc.receiveReturn(returnAmount);

    assertThat(mc.getOverallReturns().get(state), hasKey(action));
    assertThat(mc.getOverallReturns().get(state).get(action),
               contains(returnAmount));
  }

  @Test
  public void testNewActionFromSameChungToiStateAddedToOverallReturns() {
    testActionFromSameState(Game.CHUNG_TOI, false);
  }

  @Test
  public void testNewActionFromSameTicTacToeStateAddedToOverallReturns() {
    testActionFromSameState(Game.TIC_TAC_TOE, false);
  }

  @Test
  public void testSameActionFromSameChungToiStateNotAddedToOverallReturns() {
    testActionFromSameState(Game.CHUNG_TOI, true);
  }

  @Test
  public void testSameActionFromSameTicTacToeStateNotAddedToOverallReturns() {
    testActionFromSameState(Game.TIC_TAC_TOE, true);
  }

  private void testActionFromSameState(Game game, boolean sameAction) {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    State state = null;

    switch (game) {
      case CHUNG_TOI:   state = new ChungToiState(); break;
      case TIC_TAC_TOE: state = new TicTacToeNormalState(); break;
    }

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
  public void testActionFromPreviousChungToiGameAddedToOverallReturns() {
    testActionFromPreviousGameAddedToOverallReturns(Game.CHUNG_TOI);
  }

  @Test
  public void testActionFromPreviousTicTacToeGameAddedToOverallReturns() {
    testActionFromPreviousGameAddedToOverallReturns(Game.TIC_TAC_TOE);
  }

  private void testActionFromPreviousGameAddedToOverallReturns(Game game) {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    State startState = null;

    switch (game) {
      case CHUNG_TOI: startState = new ChungToiState(); break;
      case TIC_TAC_TOE: startState = new TicTacToeNormalState(); break;
    }

    Action previousGameAction = mc.chooseAction(startState);
    double previousGameReturn = (int) (Math.random()*1000 + 1);
    mc.receiveReturn(previousGameReturn);
    mc.gameOver();
    mc.initializeBeforeNewGame();

    Action newAction = mc.chooseAction(startState);

    // Choose the same action
    while (!newAction.equals(previousGameAction)) {
      newAction = mc.chooseAction(startState);
    }

    double newReturn = previousGameReturn + 1;
    mc.receiveReturn(newReturn); // +1 to make the two returns different

    assertThat(mc.getOverallReturns(), hasKey(startState));
    assertThat(mc.getOverallReturns().get(startState), hasKey(newAction));
    assertThat(mc.getOverallReturns().get(startState).get(newAction),
               contains(previousGameReturn, newReturn) /* in this order */);
  }
}
