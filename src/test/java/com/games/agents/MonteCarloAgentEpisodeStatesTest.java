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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MonteCarloAgentEpisodeStatesTest {

  private enum Game {
    CHUNG_TOI,
    TIC_TAC_TOE
  }

  public MonteCarloAgentEpisodeStatesTest() {}

  @Test
  public void testEpisodeStatesContainsAllChungToiStatesEncountered() {
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
      assertThat(mc.getEpisodeStates(), hasKey(expectedState));
    }
  }

  @Test
  public void testEpisodeStatesContainsAllTicTacToeStatesEncountered() {
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
      assertThat(mc.getEpisodeStates(), hasKey(expectedState));
    }
  }

  @Test
  public void testNewActionFromNewChungToiStateAddedToEpisodeStates() {
    testNewActionFromNewStateAddedToEpisodeStates(Game.CHUNG_TOI);
  }

  @Test
  public void testNewActionFromNewTicTacToeStateAddedToEpisodeStates() {
    testNewActionFromNewStateAddedToEpisodeStates(Game.TIC_TAC_TOE);
  }

  private void testNewActionFromNewStateAddedToEpisodeStates(Game game) {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    State state = null;

    switch (game) {
      case CHUNG_TOI:   state = new ChungToiState(); break;
      case TIC_TAC_TOE: state = new TicTacToeNormalState(); break;
    }

    Action action = mc.chooseAction(state);
    mc.receiveReturn(0);

    assertThat(mc.getEpisodeStates(), hasKey(state));
    assertThat(mc.getEpisodeStates().get(state), contains(action));
  }

  @Test
  public void testNewActionFromSameChungToiStateAddedToEpisodeStates() {
    testActionFromSameState(Game.CHUNG_TOI, false);
  }

  @Test
  public void testNewActionFromSameTicTacToeStateAddedToEpisodeStates() {
    testActionFromSameState(Game.TIC_TAC_TOE, false);
  }

  @Test
  public void testSameActionFromSameChungToiStateNotAddedToEpisodeStates() {
    testActionFromSameState(Game.CHUNG_TOI, true);
  }

  @Test
  public void testSameActionFromSameTicTacToeStateNotAddedToEpisodeStates() {
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
    mc.receiveReturn(0);

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

    mc.receiveReturn(0);

    assertThat(mc.getEpisodeStates(), hasKey(state));

    if (sameAction) {
      assertThat(mc.getEpisodeStates().get(state),
                 contains(originalAction) /* NOT newAction as well */);
    } else {
      assertThat(mc.getEpisodeStates().get(state),
                 contains(originalAction, newAction) /* in this order */);
    }
  }

  @Test
  public void testActionFromPreviousChungToiGameAddedToEpisodeStates() {
    testActionFromPreviousGameAddedToEpisodeStates(Game.CHUNG_TOI);
  }

  @Test
  public void testActionFromPreviousTicTacToeGameAddedToEpisodeStates() {
    testActionFromPreviousGameAddedToEpisodeStates(Game.TIC_TAC_TOE);
  }

  private void testActionFromPreviousGameAddedToEpisodeStates(Game game) {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);
    mc.initializeBeforeNewGame();

    State state = null;

    switch (game) {
      case CHUNG_TOI:   state = new ChungToiState(); break;
      case TIC_TAC_TOE: state = new TicTacToeNormalState(); break;
    }

    Action previousGameAction = mc.chooseAction(state);
    mc.receiveReturn(0);
    mc.gameOver();
    mc.initializeBeforeNewGame();

    Action newAction = mc.chooseAction(state);

    // Choose SAME action
    while (!newAction.equals(previousGameAction)) {
      newAction = mc.chooseAction(state);
    }

    mc.receiveReturn(0);
    assertThat(mc.getEpisodeStates(), hasKey(state));
    assertThat(mc.getEpisodeStates().get(state), contains(previousGameAction));
  }
}
