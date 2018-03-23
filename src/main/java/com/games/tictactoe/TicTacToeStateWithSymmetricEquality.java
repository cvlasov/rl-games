package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMajorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMinorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridHorizontally;
import static com.games.tictactoe.TicTacToeHelper.flipGridVertically;
import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.Player;
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.google.common.annotations.VisibleForTesting;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * State of a game of Tic-Tac-Toe that is considered equal to another state if
 * they are symmetrical along the vertical axis, horizontal axis, major
 * diagonal, minor diagonal, or a combination of these.
 */
public final class TicTacToeStateWithSymmetricEquality extends TicTacToeState {

  /**
   * Set of all grids that are symmetrical to this grid along the vertical
   * axis, horizontal axis, major diagonal, minor diagonal, or a combination of
   * these, but not including this grid.
   */
  private Set<List<TokenType>> symmetricalGrids = new HashSet<>();


  // CONSTRUCTORS

  /** Creates a state with an empty grid. */
  public TicTacToeStateWithSymmetricEquality() {
    grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    nextTurn = Player.X;
    initialize();
  }

  /**
   * Creates the state that results from applying the given action at the given
   * state.
   */
  private TicTacToeStateWithSymmetricEquality(
      TicTacToeStateWithSymmetricEquality oldState,
      TicTacToeAction action) {
    grid = new ArrayList<>(oldState.grid);
    grid.set(action.index, action.tokenType);

    switch (oldState.nextTurn) {
      case X: nextTurn = Player.O; break;
      case O: nextTurn = Player.X; break;
    }

    initialize();
  }

  /** Creates a state with the given grid and the given next player. */
  @VisibleForTesting
  TicTacToeStateWithSymmetricEquality(List<TokenType> g, Player next) {
    // TicTacToeState no-param constructor is automatically called, so reset
    this.actions = null;
    this.winner = null;

    grid = new ArrayList<>(g);
    nextTurn = next;
    initialize();
  }


  // IMPLEMENTATION OF STATE INTERFACE METHOD

  @Override
  public State applyAction(Action a) {
    return new TicTacToeStateWithSymmetricEquality(this, (TicTacToeAction) a);
  }


  // PRIVATE HELPER METHODS

  private void initialize() {
    // TicTacToeState no-param constructor is automatically called, so reset
    this.actions = null;
    this.winner = null;

    computeSymmetricalStates();
    convertToCanonicalForm();
    computeActions();
    isTerminalState();
  }

  /** Populates {@link #symmetricalGrids}. */
  private void computeSymmetricalStates() {
    symmetricalGrids.add(flipGridVertically(this.grid));
    symmetricalGrids.add(flipGridHorizontally(this.grid));
    symmetricalGrids.add(flipGridHorizontally(flipGridVertically(this.grid)));
    symmetricalGrids.add(flipGridAlongMajorDiagonal(this.grid));
    symmetricalGrids.add(flipGridHorizontally(flipGridAlongMajorDiagonal(this.grid)));
    symmetricalGrids.add(flipGridAlongMinorDiagonal(this.grid));
    symmetricalGrids.add(flipGridHorizontally(flipGridAlongMinorDiagonal(this.grid)));
  }

  /**
   * Replaces the contents of this grid with the contents of the grid that is
   * symmetrical to it and has the smallest hash code, as computed by
   * {@link TicTacToeHelper#standardHashCode()}.
   */
  private void convertToCanonicalForm() {
    List<TokenType> canonicalGrid = new ArrayList<>(this.grid);
    int minHashCode = TicTacToeHelper.standardHashCode(this.grid);

    for (List<TokenType> g : symmetricalGrids) {
      int h = TicTacToeHelper.standardHashCode(g);

      if (h < minHashCode) {
        canonicalGrid = g;
        minHashCode = h;
      }
    }

    this.grid = new ArrayList<>(canonicalGrid);
  }
}
