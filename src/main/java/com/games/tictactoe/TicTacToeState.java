package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.games.tictactoe.TicTacToeHelper.Winner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** State of a game of Tic-Tac-Toe. */
public abstract class TicTacToeState implements State {

  /**
   * Tic-Tac-Toe grid, where the list indices represent the board as:
   *    0 | 1 | 2
   *   -----------
   *    3 | 4 | 5
   *   -----------
   *    6 | 7 | 8
   */
  protected final List<TokenType> grid;

  /** List of possible actions to take from this state. */
  protected List<Action> actions = null;

  /**
   * Winning token type (or draw) if this is a terminal state, otherwise
   * {@link TicTacToeHelper#Winner.GAME_NOT_OVER}. Null if
   * {@link #checkIfTerminalState()} has not yet been called.
   */
  protected Winner winner = null;

  /** Creates a state with an empty grid. */
  protected TicTacToeState() {
    grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    computeActions();
  }

  /** Creates a copy of the given state. */
  protected TicTacToeState(TicTacToeState oldState) {
    grid = new ArrayList<>(oldState.grid);
    computeActions();
  }

  /**
   * Creates the state that results from applying the given action at the given
   * state.
   */
  protected TicTacToeState(TicTacToeState oldState, TicTacToeAction action) {
    grid = new ArrayList<>(oldState.grid);
    grid.set(action.index, action.tokenType);
    computeActions();
  }

  @Override
  public List<Action> getActions() {
    return actions;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!TicTacToeState.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final TicTacToeState other = (TicTacToeState) o;

    for (int i = 0 ; i < GRID_SIZE ; i++) {
      if (this.grid.get(i) != other.grid.get(i)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3 * grid.get(0).hashCode();
    hash +=    5 * grid.get(1).hashCode();
    hash +=    7 * grid.get(2).hashCode();
    hash +=   11 * grid.get(3).hashCode();
    hash +=   13 * grid.get(4).hashCode();
    hash +=   17 * grid.get(5).hashCode();
    hash +=   19 * grid.get(6).hashCode();
    hash +=   23 * grid.get(7).hashCode();
    hash +=   29 * grid.get(8).hashCode();
    return hash;
  }

  @Override
  public void print() {
    System.out.println(" " + grid.get(0) + " | " + grid.get(1) + " | " + grid.get(2));
    System.out.println("-----------");
    System.out.println(" " + grid.get(3) + " | " + grid.get(4) + " | " + grid.get(5));
    System.out.println("-----------");
    System.out.println(" " + grid.get(6) + " | " + grid.get(7) + " | " + grid.get(8));
    System.out.println();
  }

  /**
   * Populates {@link #actions} with all actions that can be taken from this
   * state.
   */
  protected void computeActions() {
    // Actions are only computed once
    if (actions != null) return;

    int diff = 0; // X's turn if diff = 0, O's turn if diff = 1

    for (TokenType t : grid) {
      switch (t) {
        case X:    diff += 1; break;
        case O:    diff -= 1; break;
        case NONE: break;
      }
    }

    TokenType tokenType = diff == 0 ? TokenType.X : TokenType.O;
    actions = new ArrayList<>();

    for (int i = 0; i < GRID_SIZE ; i++) {
      if (grid.get(i) == TokenType.NONE) {
        actions.add(new TicTacToeAction(i, tokenType));
      }
    }
  }

  /**
   * Returns whether or not this is a terminal state
   * <p>
   * If it is a terminal state, updates {@link #winner} accordingly.
   *
   * @return true if this is a terminal state, false otherwise
   */
  public boolean checkIfTerminalState() {
    // Only check once, so use previously computed result if called again
    if (winner != null) {
      switch (winner) {
        case X:             // fall through
        case O:             // fall through
        case DRAW:          return true;
        case GAME_NOT_OVER: return false;
      }
    }

    if (grid.get(0) != TokenType.NONE) {
      if (grid.get(0) == grid.get(1) && grid.get(1) == grid.get(2)
          || grid.get(0) == grid.get(4) && grid.get(4) == grid.get(8)
          || grid.get(0) == grid.get(3) && grid.get(3) == grid.get(6)) {
        switch (grid.get(0)) {
          case X:    winner = Winner.X; return true;
          case O:    winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }

    if (grid.get(4) != TokenType.NONE) {
      if (grid.get(3) == grid.get(4) && grid.get(4) == grid.get(5)
          || grid.get(1) == grid.get(4) && grid.get(4) == grid.get(7)
          || grid.get(2) == grid.get(4) && grid.get(4) == grid.get(6)) {
        switch (grid.get(4)) {
          case X:    winner = Winner.X; return true;
          case O:    winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }

    if (grid.get(8) != TokenType.NONE) {
      if (grid.get(6) == grid.get(7) && grid.get(7) == grid.get(8)
          || grid.get(2) == grid.get(5) && grid.get(5) == grid.get(8)) {
        switch (grid.get(8)) {
          case X:    winner = Winner.X; return true;
          case O:    winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }

    int occupiedSquares = 0;

    for (TokenType t : grid) {
      if (t != TokenType.NONE) {
        occupiedSquares += 1;
      }
    }

    if (occupiedSquares == GRID_SIZE) {
      winner = Winner.DRAW;
      return true;
    }

    winner = Winner.GAME_NOT_OVER;
    return false;
  }

  /**
   * Returns the winner of the game, if any.
   * <p>
   * Can only be called after {@link #isTerminalState}.
   *
   * @return winner of the game, if any
   */
  public Winner getWinner() {
    return winner;
  }
}
