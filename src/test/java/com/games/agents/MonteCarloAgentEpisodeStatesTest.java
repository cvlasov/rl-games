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
import org.junit.Test;

@RunWith(Parameterized.class)
public class MonteCarloAgentEpisodeStatesTest {

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

  private final double EPSILON = 0.1;
  private final double RETURN_AMOUNT = 0.0;

  @Test
  public void testEpisodeStatesContainsAllStatesEncountered() {
    MonteCarloAgent mc = new MonteCarloAgent(EPSILON);
    mc.initializeBeforeNewGame();

    State state1 = startState;
    Action action1 = mc.chooseAction(state1);
    mc.receiveReturn(RETURN_AMOUNT);

    State state2 = state1.applyAction(action1);
    Action action2 = mc.chooseAction(state2);
    mc.receiveReturn(RETURN_AMOUNT);

    State state3 = state2.applyAction(action2);
    Action action3 = mc.chooseAction(state3);
    mc.receiveReturn(RETURN_AMOUNT);

    List<State> expectedStates = new ArrayList<>();
    expectedStates.add(state1);
    expectedStates.add(state2);
    expectedStates.add(state3);

    for (State expectedState : expectedStates) {
      assertThat(mc.getEpisodeStates(), hasKey(expectedState));
    }
  }

  @Test
  public void testNewActionFromNewStateAddedToEpisodeStates() {
    MonteCarloAgent mc = new MonteCarloAgent(EPSILON);
    mc.initializeBeforeNewGame();

    State state = startState;
    Action action = mc.chooseAction(state);
    mc.receiveReturn(RETURN_AMOUNT);

    assertThat(mc.getEpisodeStates(), hasKey(state));
    assertThat(mc.getEpisodeStates().get(state), hasKey(action));
    assertThat(mc.getEpisodeStates().get(state).get(action),
               equalTo(RETURN_AMOUNT));
  }

  @Test
  public void testActionFromSameState() {
    MonteCarloAgent mc = new MonteCarloAgent(EPSILON);
    mc.initializeBeforeNewGame();

    State state = startState;
    Action originalAction = mc.chooseAction(state);
    mc.receiveReturn(RETURN_AMOUNT);

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

    mc.receiveReturn(RETURN_AMOUNT + 1);

    assertThat(mc.getEpisodeStates().get(state), hasKey(originalAction));
    assertThat(mc.getEpisodeStates().get(state).get(originalAction),
               equalTo(RETURN_AMOUNT));

    if (!sameAction) {
      assertThat(mc.getEpisodeStates().get(state), hasKey(newAction));
      assertThat(mc.getEpisodeStates().get(state).get(newAction),
                 equalTo(RETURN_AMOUNT + 1));
    }
  }

  @Test
  public void testActionFromPreviousGameAddedToEpisodeStates() {
    MonteCarloAgent mc = new MonteCarloAgent(EPSILON);
    mc.initializeBeforeNewGame();

    State state = startState;
    Action previousGameAction = mc.chooseAction(state);
    mc.receiveReturn(RETURN_AMOUNT);

    mc.gameOver();
    mc.initializeBeforeNewGame();

    Action newAction = mc.chooseAction(state);

    // Choose SAME action as in previous game
    while (!newAction.equals(previousGameAction)) {
      newAction = mc.chooseAction(state);
    }

    mc.receiveReturn(RETURN_AMOUNT + 1);

    assertThat(mc.getEpisodeStates(), hasKey(state));
    assertThat(mc.getEpisodeStates().get(state), hasKey(newAction));
    assertThat(mc.getEpisodeStates().get(state).get(newAction),
               equalTo(RETURN_AMOUNT + 1));
  }
}
