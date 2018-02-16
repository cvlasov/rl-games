package com.games.tictactoe;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.TokenType;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

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

  @VisibleForTesting
  TicTacToeNormalState(List<TokenType> g) {
    super();
    this.grid.clear();
    this.grid.addAll(g);
    this.actions = null;
    this.winner = null;
    computeActions();
    isTerminalState();  // ignore result
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
