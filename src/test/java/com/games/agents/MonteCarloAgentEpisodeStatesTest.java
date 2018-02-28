package com.games.agents;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.agents.MonteCarloAgent;
import com.games.chungtoi.ChungToiHelper;
import com.games.chungtoi.ChungToiPutAction;
import com.games.chungtoi.ChungToiState;
import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper;
import com.games.tictactoe.TicTacToeNormalState;
import com.games.tictactoe.TicTacToeState;

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MonteCarloAgentEpisodeStatesTest {

  private enum Game {
    CHUNG_TOI,
    TIC_TAC_TOE
  }

  public MonteCarloAgentEpisodeStatesTest() {}

  // TESTS RELATED TO episodeStates

  @Test
  public void testEpisodeStatesContainsAllChungToiStatesEncountered() {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);

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

    State state = null;

    switch (game) {
      case CHUNG_TOI:   state = new ChungToiState(); break;
      case TIC_TAC_TOE: state = new TicTacToeNormalState(); break;
    }

    Action action = mc.chooseAction(state);
    mc.receiveReturn(0);

    List<Action> expectedActions = new ArrayList<>();
    expectedActions.add(action);

    assertThat(mc.getEpisodeStates(), hasEntry(state, expectedActions));
  }

  @Test
  public void testNewActionFromOldChungToiStateAddedToEpisodeStates() {
    testNewActionFromOldStateAddedToEpisodeStates(Game.CHUNG_TOI);
  }

  @Test
  public void testNewActioFromOldTicTacToeStateAddedToEpisodeStates() {
    testNewActionFromOldStateAddedToEpisodeStates(Game.TIC_TAC_TOE);
  }

  private void testNewActionFromOldStateAddedToEpisodeStates(Game game) {
    Map<State, List<Action>> episodeStates = new HashMap<>();

    State oldState = null;
    Action oldAction = null;

    switch (game) {
      case CHUNG_TOI:
        oldState = new ChungToiState();
        oldAction = new ChungToiPutAction(ChungToiHelper.TokenType.X_NORMAL, 0);
        break;
      case TIC_TAC_TOE:
        oldState = new TicTacToeNormalState();
        oldAction = new TicTacToeAction(0, TicTacToeHelper.TokenType.X);
        break;
    }

    List<Action> oldActions = new ArrayList<>();
    oldActions.add(oldAction);
    episodeStates.put(oldState, oldActions);

    MonteCarloAgent mc = new MonteCarloAgent(0.1, episodeStates);

    Action newAction = oldAction;

    // Choose a different action
    while (newAction.equals(oldAction)) {
      newAction = mc.chooseAction(oldState);
    }

    mc.receiveReturn(0);

    assertThat(mc.getEpisodeStates(), hasKey(oldState));
    assertThat(mc.getEpisodeStates().get(oldState),
               contains(oldAction, newAction) /* in this order */);
  }

  @Test
  public void testOldActionFromOldChungToiStateNotAddedToEpisodeStates() {
    testOldActionFromOldStateNotAddedToEpisodeStates(Game.CHUNG_TOI);
  }

  @Test
  public void testOldActioFromOldTicTacToeStateNotAddedToEpisodeStates() {
    testOldActionFromOldStateNotAddedToEpisodeStates(Game.TIC_TAC_TOE);
  }

  private void testOldActionFromOldStateNotAddedToEpisodeStates(Game game) {
    Map<State, List<Action>> episodeStates = new HashMap<>();

    State oldState = null;
    Action oldAction = null;

    switch (game) {
      case CHUNG_TOI:
        oldState = new ChungToiState();
        oldAction = new ChungToiPutAction(ChungToiHelper.TokenType.X_NORMAL, 0);
        break;
      case TIC_TAC_TOE:
        oldState = new TicTacToeNormalState();
        oldAction = new TicTacToeAction(0, TicTacToeHelper.TokenType.X);
        break;
    }

    List<Action> oldActions = new ArrayList<>();
    oldActions.add(oldAction);
    episodeStates.put(oldState, oldActions);

    MonteCarloAgent mc = new MonteCarloAgent(0.1, episodeStates);

    Action newAction = mc.chooseAction(oldState);

    // Choose the same action
    while (!newAction.equals(oldAction)) {
      newAction = mc.chooseAction(oldState);
    }

    mc.receiveReturn(0);

    assertThat(mc.getEpisodeStates(), hasKey(oldState));
    assertThat(mc.getEpisodeStates().get(oldState),
               contains(oldAction) /* NOT newAction as well */);
  }
}
