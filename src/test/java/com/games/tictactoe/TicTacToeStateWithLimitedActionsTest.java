package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper.Player;
import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TicTacToeStateWithLimitedActionsTest {

  public TicTacToeStateWithLimitedActionsTest() {}

  @Test
  public void testAvailableActionsOnEmptyGrid() {
    /*
     * Possible actions on empty grid:
     *  * | * |
     * -----------
     *    | * |
     * -----------
     *    |   |
     */
    TicTacToeStateWithLimitedActions state =
        new TicTacToeStateWithLimitedActions();

    Set<Action> expectedActions = new HashSet<>(Arrays.asList(new Action[] {
      new TicTacToeAction(0, TokenType.X),
      new TicTacToeAction(1, TokenType.X),
      new TicTacToeAction(4, TokenType.X),
    }));

    assertThat(new HashSet<>(state.getActions()), equalTo(expectedActions));
  }

  @Test
  public void testAvailableActionsOnSemiFilledGrid() {
    /*
     * Possible actions on the following semi-filled grid:
     *  * | X |
     * -----------
     *  * | * |
     * -----------
     *  * | * |
     */
    TicTacToeStateWithLimitedActions state =
        new TicTacToeStateWithLimitedActions(
          Arrays.asList(new TokenType[] {
            TokenType.NONE, TokenType.X,    TokenType.NONE,
            TokenType.NONE, TokenType.NONE, TokenType.NONE,
            TokenType.NONE, TokenType.NONE, TokenType.NONE
          }),
          Player.O);

    Set<Action> expectedActions = new HashSet<>(Arrays.asList(new Action[] {
      new TicTacToeAction(0, TokenType.O),
      new TicTacToeAction(3, TokenType.O),
      new TicTacToeAction(4, TokenType.O),
      new TicTacToeAction(6, TokenType.O),
      new TicTacToeAction(7, TokenType.O)
    }));

    assertThat(new HashSet<>(state.getActions()), equalTo(expectedActions));
  }

  @Test
  public void testAvailableActionsOnFullGrid() {
    /*
     * Possible actions on the following full grid:
     *  O | X | X
     * -----------
     *  O | X | O
     * -----------
     *  X | O | X
     */
     TicTacToeNormalState state = new TicTacToeNormalState(
         Arrays.asList(new TokenType[] {
           TokenType.O, TokenType.X, TokenType.X,
           TokenType.O, TokenType.X, TokenType.O,
           TokenType.X, TokenType.O, TokenType.X
         }),
         Player.O);

     Set<Action> expectedActions = new HashSet<>();

     assertThat(new HashSet<>(state.getActions()), equalTo(expectedActions));
  }

  @Test
  public void testStatesNotInMonteCarloPolicy() {
    MonteCarloAgent mcAgent = new MonteCarloAgent(0.7);
    RandomAgent randAgent = new RandomAgent();
    TicTacToeGameWithLimitedActions game =
        new TicTacToeGameWithLimitedActions(mcAgent, randAgent);

    for (int i = 0 ; i < 10*1000 ; i++) {
      game.play();
    }

    TicTacToeNormalState state1 = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE, TokenType.X,    TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE
        }),
        Player.O);

    TicTacToeNormalState state2 = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE, TokenType.NONE, TokenType.X,
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE
        }),
        Player.O);

    TicTacToeNormalState state3 = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.X,
          TokenType.NONE, TokenType.NONE, TokenType.NONE
        }),
        Player.O);

    TicTacToeNormalState state4 = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.X,    TokenType.NONE, TokenType.NONE
        }),
        Player.O);

    TicTacToeNormalState state5 = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.X,    TokenType.NONE
        }),
        Player.O);

    TicTacToeNormalState state6 = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.X
        }),
        Player.O);

    List<State> states = new ArrayList<>();
    states.add(state1);
    states.add(state2);
    states.add(state3);
    states.add(state4);
    states.add(state5);
    states.add(state6);

    System.out.println(
        String.format("Policy size: %d", mcAgent.getPolicy().size()));

    for (State s : states) {
      assertThat(mcAgent.getPolicy(), not(hasKey(s)));
    }
  }
}
