package com.games.tictactoe;

import com.games.general.Action;
import com.games.general.State;

public final class TicTacToeNormalState extends TicTacToeState {

  public TicTacToeNormalState() {
    super();
  }

  private TicTacToeNormalState(TicTacToeNormalState oldState) {
    super(oldState);
  }

  private TicTacToeNormalState(
      TicTacToeNormalState oldState,
      TicTacToeAction action) {
    super(oldState, action);
  }

  @Override  // from interface State
  public State applyAction(Action a) {
    return new TicTacToeNormalState(this, (TicTacToeAction) a);
  }

  @Override  // from interface State
  public State copy() {
    return new TicTacToeNormalState(this);
  }
}
