package com.games.agents;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.agents.MonteCarloAgent;
import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeNormalState;
import com.games.tictactoe.TicTacToeState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class MonteCarloAgentTest {

  public MonteCarloAgentTest() {}

  @Test
  public void testEpisodeStates() {
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

    HashMap<State, List<Action>> expectedEpisodeStates = new HashMap<>();

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
}
