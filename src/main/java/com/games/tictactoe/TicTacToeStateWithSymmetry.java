package com.games.tictactoe;

import com.games.general.Action;
import com.games.general.State;

/**
 * State of a game of Tic-Tac-Toe that is considered equal to another state if
 * they are symmetrical along the vertical or horizontal axis or along either
 * diagonal.
 */
public final class TicTacToeStateWithSymmetry extends TicTacToeState {

  public TicTacToeStateWithSymmetry() {
    super();
  }

  private TicTacToeStateWithSymmetry(TicTacToeStateWithSymmetry oldState) {
    super(oldState);
  }

  private TicTacToeStateWithSymmetry(
      TicTacToeStateWithSymmetry oldState,
      TicTacToeAction action) {
    super(oldState, action);
  }

  @Override  // from interface State
  public State applyAction(Action a) {
    return new TicTacToeStateWithSymmetry(this, (TicTacToeAction) a);
  }

  @Override  // from interface State
  public State copy() {
    return new TicTacToeStateWithSymmetry(this);
  }

  @Override
  // TODO: write implementation that produces desired behaviour
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  // TODO: write implementation that produces desired behaviour
  public int hashCode() {
    return super.hashCode();
  }
}
