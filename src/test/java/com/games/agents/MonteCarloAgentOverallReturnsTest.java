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

public class MonteCarloAgentOverallReturnsTest {

  private enum Game {
    CHUNG_TOI,
    TIC_TAC_TOE
  }

  public MonteCarloAgentOverallReturnsTest() {}

  @Test
  public void testOverallReturnsContainsAllChungToiStatesEncountered() {
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
      assertThat(mc.getOverallReturns(), hasKey(expectedState));
    }
  }

  @Test
  public void testOverallReturnsContainsAllTicTacToeStatesEncountered() {
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
  public void testNewActionFromNewChungToiStateAddedToOverallReturns() {
    testNewActionFromNewStateAddedToOverallReturns(Game.CHUNG_TOI);
  }

  @Test
  public void testNewActionFromNewTicTacToeStateAddedToOverallReturns() {
    testNewActionFromNewStateAddedToOverallReturns(Game.TIC_TAC_TOE);
  }

  private void testNewActionFromNewStateAddedToOverallReturns(Game game) {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);

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
  public void testNewActionFromOldChungToiStateAddedToOverallReturns() {
    testNewActionFromOldStateAddedToOverallReturns(Game.CHUNG_TOI);
  }

  @Test
  public void testNewActioFromOldTicTacToeStateAddedToOverallReturns() {
    testNewActionFromOldStateAddedToOverallReturns(Game.TIC_TAC_TOE);
  }

  private void testNewActionFromOldStateAddedToOverallReturns(Game game) {
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

    Map<State, List<Action>> episodeStates = new HashMap<>();
    episodeStates.put(oldState, oldActions);

    List<Double> oldReturns = new ArrayList<>();
    double oldReturn = (int) (Math.random()*1000 + 1);
    oldReturns.add(oldReturn);

    HashMap<Action, List<Double>> oldActionReturns = new HashMap<>();
    oldActionReturns.put(oldAction, oldReturns);

    HashMap<State, HashMap<Action, List<Double>>> overallReturns = new HashMap<>();
    overallReturns.put(oldState, oldActionReturns);

    MonteCarloAgent mc = new MonteCarloAgent(0.1,
                                             episodeStates,
                                             overallReturns);

    Action newAction = oldAction;

    // Choose a different action
    while (newAction.equals(oldAction)) {
      newAction = mc.chooseAction(oldState);
    }

    double newReturn = (int) (Math.random()*1000 + 1);
    mc.receiveReturn(newReturn);

    assertThat(mc.getOverallReturns(), hasKey(oldState));
    assertThat(mc.getOverallReturns().get(oldState), hasKey(newAction));
    assertThat(mc.getOverallReturns().get(oldState).get(newAction),
               contains(newReturn) /* in this order */);
  }

  @Test
  public void testOldActionFromOldChungToiStateNotAddedToOverallReturns() {
    testOldActionFromOldStateNotAddedToOverallReturns(Game.CHUNG_TOI);
  }

  @Test
  public void testOldActioFromOldTicTacToeStateNotAddedToOverallReturns() {
    testOldActionFromOldStateNotAddedToOverallReturns(Game.TIC_TAC_TOE);
  }

  private void testOldActionFromOldStateNotAddedToOverallReturns(Game game) {
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

    Map<State, List<Action>> episodeStates = new HashMap<>();
    episodeStates.put(oldState, oldActions);

    List<Double> oldReturns = new ArrayList<>();
    double oldReturn = (int) (Math.random()*1000 + 1);
    oldReturns.add(oldReturn);

    HashMap<Action, List<Double>> oldActionReturns = new HashMap<>();
    oldActionReturns.put(oldAction, oldReturns);

    HashMap<State, HashMap<Action, List<Double>>> overallReturns = new HashMap<>();
    overallReturns.put(oldState, oldActionReturns);

    MonteCarloAgent mc = new MonteCarloAgent(0.1,
                                             episodeStates,
                                             overallReturns);

    Action newAction = mc.chooseAction(oldState);

    // Choose the same action
    while (!newAction.equals(oldAction)) {
      newAction = mc.chooseAction(oldState);
    }

    mc.receiveReturn(oldReturn + 1); // +1 to make the two returns different

    assertThat(mc.getOverallReturns(), hasKey(oldState));
    assertThat(mc.getOverallReturns().get(oldState), hasKey(newAction));
    assertThat(mc.getOverallReturns().get(oldState).get(newAction),
               contains(oldReturn) /* NOT the new return as well */);
  }
}
