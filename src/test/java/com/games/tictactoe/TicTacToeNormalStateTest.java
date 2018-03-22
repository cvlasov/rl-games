package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.games.general.Action;
import com.games.tictactoe.TicTacToeHelper.Player;
import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
  public void testInequalityWithDifferentGrids() {
    TicTacToeNormalState state1 = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.X,    TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE
        }),
        Player.X);
		TicTacToeNormalState state2 = new TicTacToeNormalState();
    assertThat(state1, not(equalTo(state2)));
	}

  @Test
  public void testInequalityWithDifferentNextTurns() {
    TicTacToeNormalState state1 = new TicTacToeNormalState(
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE)),
        Player.X);
    TicTacToeNormalState state2 = new TicTacToeNormalState(
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE)),
        Player.O);
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
    TicTacToeNormalState state1 = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.X,    TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.NONE
        }),
        Player.O);
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
    TicTacToeNormalState state = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.O, TokenType.X, TokenType.X,
          TokenType.O, TokenType.X, TokenType.O,
          TokenType.X, TokenType.O, TokenType.X
        }),
        Player.O);

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
    TicTacToeNormalState state = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.O,    TokenType.X,    TokenType.NONE,
          TokenType.NONE, TokenType.O,    TokenType.X,
          TokenType.X,    TokenType.NONE, TokenType.O
        }),
        Player.X);

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
    TicTacToeNormalState state = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.O, TokenType.X, TokenType.X,
          TokenType.X, TokenType.O, TokenType.O,
          TokenType.X, TokenType.O, TokenType.X
        }),
        Player.O);

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
    TicTacToeNormalState state = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.O,    TokenType.NONE, TokenType.NONE,
          TokenType.NONE, TokenType.NONE, TokenType.X,
          TokenType.NONE, TokenType.NONE, TokenType.NONE
        }),
        Player.X);

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
    TicTacToeNormalState state = new TicTacToeNormalState();

    Set<Action> expectedActions = new HashSet<>(Arrays.asList(new Action[] {
      new TicTacToeAction(0, TokenType.X),
      new TicTacToeAction(1, TokenType.X),
      new TicTacToeAction(2, TokenType.X),
      new TicTacToeAction(3, TokenType.X),
      new TicTacToeAction(4, TokenType.X),
      new TicTacToeAction(5, TokenType.X),
      new TicTacToeAction(6, TokenType.X),
      new TicTacToeAction(7, TokenType.X),
      new TicTacToeAction(8, TokenType.X)
    }));

    assertThat(new HashSet<>(state.getActions()), equalTo(expectedActions));
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
    TicTacToeNormalState state = new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE, TokenType.X,    TokenType.NONE,
          TokenType.X,    TokenType.NONE, TokenType.O,
          TokenType.O,    TokenType.X,    TokenType.NONE
        }),
        Player.O);

    Set<Action> expectedActions = new HashSet<>(Arrays.asList(new Action[] {
      new TicTacToeAction(0, TokenType.O),
      new TicTacToeAction(2, TokenType.O),
      new TicTacToeAction(4, TokenType.O),
      new TicTacToeAction(8, TokenType.O)
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
}
