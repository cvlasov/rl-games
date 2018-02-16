package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMajorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMinorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridHorizontally;
import static com.games.tictactoe.TicTacToeHelper.flipGridVertically;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.google.common.annotations.VisibleForTesting;

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * State of a game of Tic-Tac-Toe that is considered equal to another state if
 * they are symmetrical along the vertical or horizontal axis or along either
 * diagonal.
 */
public final class TicTacToeStateWithSymmetricEquality extends TicTacToeState {

  private int id = Integer.MAX_VALUE;

  @VisibleForTesting
  Set<List<TokenType>> symmetricalGrids = new HashSet<>();

  public TicTacToeStateWithSymmetricEquality() {
    super();
    initialize();
  }

  private TicTacToeStateWithSymmetricEquality(
      TicTacToeStateWithSymmetricEquality oldState) {
    super(oldState);
    initialize();
  }

  private TicTacToeStateWithSymmetricEquality(
      TicTacToeStateWithSymmetricEquality oldState,
      TicTacToeAction action) {
    super(oldState, action);
    initialize();
  }

  @VisibleForTesting
  TicTacToeStateWithSymmetricEquality(List<TokenType> g) {
    super();
    this.grid.clear();
    this.grid.addAll(g);
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

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!TicTacToeStateWithSymmetricEquality.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final TicTacToeStateWithSymmetricEquality other =
        (TicTacToeStateWithSymmetricEquality) o;
    return this.id == other.getId();
  }

  public int getId() {
    return id;
  }

  private void initialize() {
    computeSymmetricalStates();
    computeID();
  }

  private void computeSymmetricalStates() {
    symmetricalGrids.add(this.grid);
    symmetricalGrids.add(flipGridVertically(this.grid));
    symmetricalGrids.add(flipGridHorizontally(this.grid));
    symmetricalGrids.add(flipGridHorizontally(flipGridVertically(this.grid)));
    symmetricalGrids.add(flipGridAlongMajorDiagonal(this.grid));
    symmetricalGrids.add(flipGridAlongMinorDiagonal(this.grid));
  }

  /**
   * Computes the ID of a state to be the minimum (standard) hashcode of
   * any of its symmetrical states (including itself). In this way, all states
   * that are symmetrical to each other will have the same ID.
   */
  private void computeID() {
    for (List<TokenType> g : symmetricalGrids) {
      id = Math.min(id, TicTacToeHelper.standardHashCode(g));
    }
  }
}
