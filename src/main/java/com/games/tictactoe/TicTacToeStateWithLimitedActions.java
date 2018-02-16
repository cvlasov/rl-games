package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMajorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMinorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridHorizontally;
import static com.games.tictactoe.TicTacToeHelper.flipGridVertically;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * State of a game of Tic-Tac-Toe where the list of possible actions does not
 * include any two actions that result in states that are symmetrical along the
 * vertical or horizontal axis or along either diagonal.
 */
public final class TicTacToeStateWithLimitedActions extends TicTacToeState {

  public TicTacToeStateWithLimitedActions() {
    super();
  }

  private TicTacToeStateWithLimitedActions(
      TicTacToeStateWithLimitedActions oldState) {
    super(oldState);
  }

  private TicTacToeStateWithLimitedActions(
      TicTacToeStateWithLimitedActions oldState,
      TicTacToeAction action) {
    super(oldState, action);
  }

  @VisibleForTesting
  TicTacToeStateWithLimitedActions(List<TokenType> g) {
    grid = new ArrayList<>(g);
    computeActions();
    isTerminalState();  // ignore result
  }

  @Override  // from interface State
  public State applyAction(Action a) {
    return new TicTacToeStateWithLimitedActions(this, (TicTacToeAction) a);
  }

  @Override  // from interface State
  public State copy() {
    return new TicTacToeStateWithLimitedActions(this);
  }

  /**
   * Populates {@link #actions} with all actions that can be taken from this
   * state such that no two actions result in states that are symmetrical along
   * the vertical or horizontal axis or along either diagonal.
   */
  @Override
  protected void computeActions() {
    // Actions are only computed once
    if (actions != null) return;

    int diff = 0;  // X's turn if diff = 0, O's turn if diff = 1

    for (TokenType t : grid) {
      switch (t) {
        case X:    diff += 1; break;
        case O:    diff -= 1; break;
        case NONE: break;
      }
    }

    TokenType tokenType = diff == 0 ? TokenType.X : TokenType.O;

    List<Action> possibleActions = new ArrayList<>();
    Set<List<TokenType>> possibleNextStates = new HashSet<>();

    for (int i = 0; i < GRID_SIZE ; i++) {
      if (grid.get(i) != TokenType.NONE) continue;

      TicTacToeAction action = new TicTacToeAction(i, tokenType);
      List<TokenType> nextGridState = new ArrayList<>(this.grid);
      nextGridState.set(action.index, action.tokenType);

      List<TokenType> verticalFlip = flipGridVertically(nextGridState);
      List<TokenType> horizontalFlip = flipGridHorizontally(nextGridState);
      List<TokenType> doubleFlip = flipGridHorizontally(verticalFlip);
      List<TokenType> majorDiagonalFlip =
          flipGridAlongMajorDiagonal(nextGridState);
      List<TokenType> minorDiagonalFlip =
          flipGridAlongMinorDiagonal(nextGridState);

      if (possibleNextStates.contains(nextGridState)
          || possibleNextStates.contains(verticalFlip)
          || possibleNextStates.contains(horizontalFlip)
          || possibleNextStates.contains(doubleFlip)
          || possibleNextStates.contains(majorDiagonalFlip)
          || possibleNextStates.contains(minorDiagonalFlip)) {
        continue;
      }

      possibleActions.add(action);
      possibleNextStates.add(nextGridState);
    }

    actions = possibleActions;
  }
}
