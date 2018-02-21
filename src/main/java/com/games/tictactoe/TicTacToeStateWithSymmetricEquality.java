package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMajorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMinorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridHorizontally;
import static com.games.tictactoe.TicTacToeHelper.flipGridVertically;
import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;

import com.games.general.Action;
import com.games.general.State;
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
 * they are symmetrical along the vertical or horizontal axis or along either
 * diagonal.
 */
public final class TicTacToeStateWithSymmetricEquality extends TicTacToeState {

  private Set<List<TokenType>> symmetricalGrids = new HashSet<>();

  public TicTacToeStateWithSymmetricEquality() {
    // TicTacToeState no-param constructor is automatically called, so reset
    this.actions = null;
    this.winner = null;

    grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    initialize();
  }

  private TicTacToeStateWithSymmetricEquality(
      TicTacToeStateWithSymmetricEquality oldState) {
    // TicTacToeState no-param constructor is automatically called, so reset
    this.actions = null;
    this.winner = null;

    grid = new ArrayList<>(oldState.grid);
    initialize();
  }

  private TicTacToeStateWithSymmetricEquality(
      TicTacToeStateWithSymmetricEquality oldState,
      TicTacToeAction action) {
    // TicTacToeState no-param constructor is automatically called, so reset
    this.actions = null;
    this.winner = null;

    grid = new ArrayList<>(oldState.grid);
    grid.set(action.index, action.tokenType);
    initialize();
  }

  @VisibleForTesting
  TicTacToeStateWithSymmetricEquality(List<TokenType> g) {
    // TicTacToeState no-param constructor is automatically called, so reset
    this.actions = null;
    this.winner = null;

    grid = new ArrayList<>(g);
    initialize();
  }

  @Override  // from interface State
  public State applyAction(Action a) {
    return new TicTacToeStateWithSymmetricEquality(this, (TicTacToeAction) a);
  }

  @Override  // from interface State
  public State copy() {
    return new TicTacToeStateWithSymmetricEquality(this);
  }

  private void initialize() {
    // TicTacToeState no-param constructor is automatically called, so reset
    this.actions = null;
    this.winner = null;

    computeSymmetricalStates();
    convertToCanonicalForm();
    computeActions();
    isTerminalState();
  }

  private void computeSymmetricalStates() {
    symmetricalGrids.add(flipGridVertically(this.grid));
    symmetricalGrids.add(flipGridHorizontally(this.grid));
    symmetricalGrids.add(flipGridHorizontally(flipGridVertically(this.grid)));
    symmetricalGrids.add(flipGridAlongMajorDiagonal(this.grid));
    symmetricalGrids.add(flipGridHorizontally(flipGridAlongMajorDiagonal(this.grid)));
    symmetricalGrids.add(flipGridAlongMinorDiagonal(this.grid));
    symmetricalGrids.add(flipGridHorizontally(flipGridAlongMinorDiagonal(this.grid)));
  }

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
