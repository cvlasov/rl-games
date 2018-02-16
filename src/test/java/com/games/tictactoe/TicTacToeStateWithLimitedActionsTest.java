package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.general.Action;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.ArrayList;
import java.util.Collections;
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

    List<TokenType> grid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(new TicTacToeAction(0, TokenType.X));
    expectedActions.add(new TicTacToeAction(1, TokenType.X));
    expectedActions.add(new TicTacToeAction(4, TokenType.X));

    checkSameActions(expectedActions, grid);
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

    List<TokenType> grid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(1, TokenType.X);

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(new TicTacToeAction(0, TokenType.O));
    expectedActions.add(new TicTacToeAction(3, TokenType.O));
    expectedActions.add(new TicTacToeAction(4, TokenType.O));
    expectedActions.add(new TicTacToeAction(6, TokenType.O));
    expectedActions.add(new TicTacToeAction(7, TokenType.O));

    checkSameActions(expectedActions, grid);
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

    List<TokenType> grid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.O);
    grid.set(1, TokenType.X);
    grid.set(2, TokenType.X);
    grid.set(3, TokenType.O);
    grid.set(4, TokenType.X);
    grid.set(5, TokenType.O);
    grid.set(6, TokenType.X);
    grid.set(7, TokenType.O);
    grid.set(8, TokenType.X);

    Set<Action> expectedActions = new HashSet<>(); // empty set (ie. no actions)

    checkSameActions(expectedActions, grid);
  }

  /**
    * Asserts that the actions available from the given grid are correct.
    *
    * @param expectedActions set of actions that should be available
    * @param grid            Tic-Tac-Toe grid
    */
  private void checkSameActions(Set<Action> expectedActions,
                                List<TokenType> grid) {
    TicTacToeStateWithLimitedActions state =
        new TicTacToeStateWithLimitedActions(grid);
    Set<Action> actualActions = new HashSet<>(state.getActions());
    assertThat(actualActions, equalTo(expectedActions));
  }
}
