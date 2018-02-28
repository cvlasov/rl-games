package com.games.agents;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.agents.MonteCarloAgent;
import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.games.tictactoe.TicTacToeNormalState;
import com.games.tictactoe.TicTacToeState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MonteCarloAgentTest {

  public MonteCarloAgentTest() {}

  @Test
  public void testEpisodeStatesContainsAllStatesEncountered() {
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
  public void testNewStateNewActionAddedToEpisodeStates() {
    MonteCarloAgent mc = new MonteCarloAgent(0.1);

    State state = new TicTacToeNormalState();
    Action action = mc.chooseAction(state);
    mc.receiveReturn(0);

    List<Action> expectedActions = new ArrayList<>();
    expectedActions.add(action);

    assertThat(mc.getEpisodeStates(), hasEntry(state, expectedActions));
  }

  @Test
  public void testOldStateNewActionAddedToEpisodeStates() {
    Map<State, List<Action>> episodeStates = new HashMap<>();
    State oldState = new TicTacToeNormalState();
    Action oldAction = new TicTacToeAction(0, TokenType.X);
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
