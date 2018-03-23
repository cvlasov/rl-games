package com.games.tictactoe;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.Player;
import com.games.tictactoe.TicTacToeHelper.TokenType;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

public final class TicTacToeNormalState extends TicTacToeState {

  // CONSTRUCTORS

  /** Creates a state with an empty grid. */
  public TicTacToeNormalState() {
    super();
  }

  /**
   * Creates the state that results from applying the given action at the given
   * state.
   */
  private TicTacToeNormalState(
      TicTacToeNormalState oldState,
      TicTacToeAction action) {
    super(oldState, action);
  }


  /** Creates a state with the given grid and the given next player. */
  @VisibleForTesting
  TicTacToeNormalState(List<TokenType> g, Player next) {
    // TicTacToeState no-param constructor is automatically called, so reset
    this.actions = null;
    this.winner = null;

    grid = new ArrayList<>(g);
    nextTurn = next;
    computeActions();
    isTerminalState();
  }


  // IMPLEMENTATION OF STATE INTERFACE METHOD

  @Override
  public State applyAction(Action a) {
    return new TicTacToeNormalState(this, (TicTacToeAction) a);
  }
}
