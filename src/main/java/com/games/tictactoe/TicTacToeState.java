package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.Player;
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
  protected List<TokenType> grid;

  /** List of possible actions to take from this state. */
  protected List<Action> actions = null;

  /** Player whose turn it is to move from this state. */
  protected Player nextTurn;

  /**
   * Winning token type (or draw) if this is a terminal state, otherwise
   * {@link TicTacToeHelper#Winner.GAME_NOT_OVER}. Null if
   * {@link #isTerminalState()} has not yet been called.
   */
  protected Winner winner = null;


  // CONSTRUCTORS

  protected TicTacToeState() {
    grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    nextTurn = Player.X;
    computeActions();
    isTerminalState();  // ignore result
  }

  protected TicTacToeState(TicTacToeState oldState, TicTacToeAction action) {
    grid = new ArrayList<>(oldState.grid);
    grid.set(action.index, action.tokenType);
    switch (oldState.nextTurn) {
      case X: nextTurn = Player.O; break;
      case O: nextTurn = Player.X; break;
    }
    computeActions();
    isTerminalState();  // ignore result
  }


  // OVERRITTEN METHODS FROM OBJECT

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!TicTacToeState.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final TicTacToeState other = (TicTacToeState) o;

    return this.grid.equals(other.grid)
           && this.actions.equals(other.actions)
           && this.nextTurn == other.nextTurn
           && this.winner == other.winner;
  }

  @Override
  public int hashCode() {
    return TicTacToeHelper.standardHashCode(this.grid);
  }


  // IMPLEMENTATIONS OF STATE INTERFACE METHODS

  @Override
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Returns whether or not this is a terminal state.
   * <p>
   * If it is a terminal state, updates {@link #winner} accordingly.
   *
   * @return true if this is a terminal state, false otherwise
   */
  @Override
  public boolean isTerminalState() {
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

  @Override
  public void print() {
    TicTacToeHelper.print(this.grid);
  }

  /**
   * Returns the winner of the game, if any.
   * <p>
   * Can only be called after {@link #isTerminalState()}.
   *
   * @return winner of the game, if any
   */
  Winner getWinner() {
    return winner;
  }


  // HELPER METHOD

  /**
   * Populates {@link #actions} with all actions that can be taken from this
   * state.
   */
  protected void computeActions() {
    // Actions are only computed once
    if (actions != null) return;

    TokenType tokenType = (nextTurn == Player.X) ? TokenType.X : TokenType.O;
    actions = new ArrayList<>();

    for (int i = 0; i < GRID_SIZE ; i++) {
      if (grid.get(i) == TokenType.NONE) {
        actions.add(new TicTacToeAction(i, tokenType));
      }
    }
  }
}
