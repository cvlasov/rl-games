package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.general.Action;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.Arrays;
import java.util.HashSet;
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
          }));

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
         }));

     Set<Action> expectedActions = new HashSet<>();

     assertThat(new HashSet<>(state.getActions()), equalTo(expectedActions));
  }
}
