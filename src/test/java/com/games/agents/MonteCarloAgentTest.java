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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MonteCarloAgentTest {

  private enum Game {
    CHUNG_TOI,
    TIC_TAC_TOE
  }

  public MonteCarloAgentTest() {}

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

    Map<State, List<Action>> expectedEpisodeStates = new HashMap<>();

    List<Action> a1 = new ArrayList<>(); a1.add(action1);
    List<Action> a2 = new ArrayList<>(); a2.add(action2);
    List<Action> a3 = new ArrayList<>(); a3.add(action3);

    expectedEpisodeStates.put(state1, a1);
    expectedEpisodeStates.put(state2, a2);
    expectedEpisodeStates.put(state3, a3);

    assertThat(mc.getEpisodeStates().keySet(),
               equalTo(expectedEpisodeStates.keySet()));

    for (State s : mc.getEpisodeStates().keySet()) {
      assertThat(new HashSet<>(mc.getEpisodeStates().get(s)),
                 equalTo(new HashSet<>(expectedEpisodeStates.get(s))));
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

    Map<State, List<Action>> expectedEpisodeStates = new HashMap<>();

    List<Action> a1 = new ArrayList<>(); a1.add(action1);
    List<Action> a2 = new ArrayList<>(); a2.add(action2);
    List<Action> a3 = new ArrayList<>(); a3.add(action3);

    expectedEpisodeStates.put(state1, a1);
    expectedEpisodeStates.put(state2, a2);
    expectedEpisodeStates.put(state3, a3);

    assertThat(mc.getEpisodeStates().keySet(),
               equalTo(expectedEpisodeStates.keySet()));

    for (State s : mc.getEpisodeStates().keySet()) {
      assertThat(new HashSet<>(mc.getEpisodeStates().get(s)),
                 equalTo(new HashSet<>(expectedEpisodeStates.get(s))));
    }
  }

  @Test
  public void testNewChungToiStateNewActionAddedToEpisodeStates() {
    testNewStateNewActionAddedToEpisodeStates(Game.CHUNG_TOI);
  }

  @Test
  public void testNewTicTacToeStateNewActionAddedToEpisodeStates() {
    testNewStateNewActionAddedToEpisodeStates(Game.TIC_TAC_TOE);
  }

  private void testNewStateNewActionAddedToEpisodeStates(Game game) {
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
  public void testOldChungToiStateNewActionAddedToEpisodeStates() {
    testOldStateNewActionAddedToEpisodeStates(Game.CHUNG_TOI);
  }

  @Test
  public void testOldTicTacToeStateNewActionAddedToEpisodeStates() {
    testOldStateNewActionAddedToEpisodeStates(Game.TIC_TAC_TOE);
  }

  private void testOldStateNewActionAddedToEpisodeStates(Game game) {
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

    List<Action> expectedActions = new ArrayList<>();
    expectedActions.add(oldAction);
    expectedActions.add(newAction);

    assertThat(mc.getEpisodeStates(), hasKey(oldState));
    assertThat(mc.getEpisodeStates().get(oldState),
               contains(oldAction, newAction) /* in this order */);
  }
}
