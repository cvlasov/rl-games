package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.games.general.Action;
import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TicTacToeNormalStateTest {

  public TicTacToeNormalStateTest() {}

  @Test
	public void testEquality() {
	  TicTacToeNormalState state1 = new TicTacToeNormalState();
		TicTacToeNormalState state2 = new TicTacToeNormalState();
    assertThat(state1, equalTo(state2));
	}

  @Test
  public void testInequality() {
    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.X);

	  TicTacToeNormalState state1 = new TicTacToeNormalState(grid);
		TicTacToeNormalState state2 = new TicTacToeNormalState();
    assertThat(state1, not(equalTo(state2)));
	}

  @Test
	public void testHashCodeEquality() {
	  TicTacToeNormalState state1 = new TicTacToeNormalState();
		TicTacToeNormalState state2 = new TicTacToeNormalState();
    assertThat(state1.hashCode(), equalTo(state2.hashCode()));
	}

  @Test
	public void testHashCodeInequality() {
    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.X);

	  TicTacToeNormalState state1 = new TicTacToeNormalState(grid);
		TicTacToeNormalState state2 = new TicTacToeNormalState();
    assertThat(state1.hashCode(), not(equalTo(state2.hashCode())));
	}

  @Test
  public void testIsTerminalStateIsTrueForXWin() {
    /*
     * This is a terminal state since X won:
     *  O | X | X
     * -----------
     *  O | X | O
     * -----------
     *  X | O | X
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.O);
    grid.set(1, TokenType.X);
    grid.set(2, TokenType.X);
    grid.set(3, TokenType.O);
    grid.set(4, TokenType.X);
    grid.set(5, TokenType.O);
    grid.set(6, TokenType.X);
    grid.set(7, TokenType.O);
    grid.set(8, TokenType.X);

    TicTacToeNormalState state = new TicTacToeNormalState(grid);
    assertTrue(state.isTerminalState());
  }

  @Test
  public void testIsTerminalStateIsTrueForOWin() {
    /*
     * This is a terminal state since O won:
     *  O | X |
     * -----------
     *    | O | X
     * -----------
     *  X |   | O
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.O);
    grid.set(1, TokenType.X);
    grid.set(4, TokenType.O);
    grid.set(5, TokenType.X);
    grid.set(6, TokenType.X);
    grid.set(8, TokenType.O);

    TicTacToeNormalState state = new TicTacToeNormalState(grid);
    assertTrue(state.isTerminalState());
  }

  @Test
  public void testIsTerminalStateIsTrueForDraw() {
    /*
     * This is a terminal state since it's a draw:
     *  O | X | X
     * -----------
     *  X | O | O
     * -----------
     *  X | O | X
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.O);
    grid.set(1, TokenType.X);
    grid.set(2, TokenType.X);
    grid.set(3, TokenType.X);
    grid.set(4, TokenType.O);
    grid.set(5, TokenType.O);
    grid.set(6, TokenType.X);
    grid.set(7, TokenType.O);
    grid.set(8, TokenType.X);

    TicTacToeNormalState state = new TicTacToeNormalState(grid);
    assertTrue(state.isTerminalState());
  }

  @Test
  public void testIsTerminalStateIsFalseForUnfinishedGame() {
    /*
     * This is NOT a terminal state:
     *  O |   |
     * -----------
     *    |   | X
     * -----------
     *    |   |
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.O);
    grid.set(5, TokenType.X);

    TicTacToeNormalState state = new TicTacToeNormalState(grid);
    assertFalse(state.isTerminalState());
  }

  @Test
  public void testAvailableActionsOnEmptyGrid() {
    /*
     * Possible actions on empty grid:
     *  * | * | *
     * -----------
     *  * | * | *
     * -----------
     *  * | * | *
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(new TicTacToeAction(0, TokenType.X));
    expectedActions.add(new TicTacToeAction(1, TokenType.X));
    expectedActions.add(new TicTacToeAction(2, TokenType.X));
    expectedActions.add(new TicTacToeAction(3, TokenType.X));
    expectedActions.add(new TicTacToeAction(4, TokenType.X));
    expectedActions.add(new TicTacToeAction(5, TokenType.X));
    expectedActions.add(new TicTacToeAction(6, TokenType.X));
    expectedActions.add(new TicTacToeAction(7, TokenType.X));
    expectedActions.add(new TicTacToeAction(8, TokenType.X));

    checkSameActions(expectedActions, grid);
  }

  @Test
  public void testAvailableActionsOnSemiFilledGrid() {
    /*
     * Possible actions on the following semi-filled grid:
     *  * | X | *
     * -----------
     *  X | * | O
     * -----------
     *  O | X | *
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(1, TokenType.X);
    grid.set(3, TokenType.X);
    grid.set(5, TokenType.O);
    grid.set(6, TokenType.O);
    grid.set(7, TokenType.X);

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(new TicTacToeAction(0, TokenType.O));
    expectedActions.add(new TicTacToeAction(2, TokenType.O));
    expectedActions.add(new TicTacToeAction(4, TokenType.O));
    expectedActions.add(new TicTacToeAction(8, TokenType.O));

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

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.O);
    grid.set(1, TokenType.X);
    grid.set(2, TokenType.X);
    grid.set(3, TokenType.O);
    grid.set(4, TokenType.X);
    grid.set(5, TokenType.O);
    grid.set(6, TokenType.X);
    grid.set(7, TokenType.O);
    grid.set(8, TokenType.X);

    Set<Action> expectedActions = new HashSet<>();
    checkSameActions(expectedActions, grid);
  }

  private void checkSameActions(Set<Action> expectedActions, List<TokenType> grid) {
    TicTacToeNormalState state = new TicTacToeNormalState(grid);
    Set<Action> actualActions = new HashSet<>(state.getActions());
    assertThat(actualActions, equalTo(expectedActions));
  }
}
