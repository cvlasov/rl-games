package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMajorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMinorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridHorizontally;
import static com.games.tictactoe.TicTacToeHelper.flipGridVertically;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.Player;
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * State of a game of Tic-Tac-Toe where the list of possible actions does not
 * include any two actions that result in states that are symmetrical along the
 * vertical axis, horizontal axis, major diagonal, minor diagonal, or a
 * combination of these.
 */
public final class TicTacToeStateWithLimitedActions extends TicTacToeState {

  // CONSTRUCTORS

  /** Creates a state with an empty grid. */
  public TicTacToeStateWithLimitedActions() {
    super();
  }

  /**
   * Creates the state that results from applying the given action at the given
   * state.
   */
  private TicTacToeStateWithLimitedActions(
      TicTacToeStateWithLimitedActions oldState,
      TicTacToeAction action) {
    super(oldState, action);
  }

  /** Creates a state with the given grid and the given next player. */
  @VisibleForTesting
  TicTacToeStateWithLimitedActions(List<TokenType> g, Player next) {
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
    return new TicTacToeStateWithLimitedActions(this, (TicTacToeAction) a);
  }


  // HELPER METHOD

  /**
   * Populates {@link #actions} with all actions that can be taken from this
   * state such that no two actions result in states that are symmetrical along
   * the vertical or horizontal axis or along either diagonal.
   */
  @Override
  protected void computeActions() {
    // Actions are only computed once
    if (actions != null) return;

    TokenType tokenType = (nextTurn == Player.X) ? TokenType.X : TokenType.O;
    List<Action> possibleActions = new ArrayList<>();
    Set<List<TokenType>> possibleNextGrids = new HashSet<>();

    for (int i = 0; i < GRID_SIZE ; i++) {
      if (grid.get(i) != TokenType.NONE) continue;

      List<TokenType> nextGrid = new ArrayList<>(this.grid);
      nextGrid.set(action.index, action.tokenType);

      List<TokenType> verticalFlip = flipGridVertically(nextGrid);
      List<TokenType> horizontalFlip = flipGridHorizontally(nextGrid);
      List<TokenType> verticalAndHorizontalFlip =
          flipGridHorizontally(flipGridVertically(nextGrid));
      List<TokenType> majorDiagonalFlip =
          flipGridAlongMajorDiagonal(nextGrid);
      List<TokenType> majorDiagonalAndHorizontalFlip =
          flipGridHorizontally(flipGridAlongMajorDiagonal(nextGrid));
      List<TokenType> minorDiagonalFlip =
          flipGridAlongMinorDiagonal(nextGrid);
      List<TokenType> minorDiagonalAndHorizontalFlip =
          flipGridHorizontally(flipGridAlongMinorDiagonal(nextGrid));

      if (possibleNextGrids.contains(nextGrid)
          || possibleNextGrids.contains(verticalFlip)
          || possibleNextGrids.contains(horizontalFlip)
          || possibleNextGrids.contains(verticalAndHorizontalFlip)
          || possibleNextGrids.contains(majorDiagonalFlip)
          || possibleNextGrids.contains(majorDiagonalAndHorizontalFlip)
          || possibleNextGrids.contains(minorDiagonalFlip)
          || possibleNextGrids.contains(minorDiagonalAndHorizontalFlip)) {
        continue;
      }

      possibleActions.add(new TicTacToeAction(i, tokenType));
      possibleNextGrids.add(nextGrid);
    }

    actions = possibleActions;
  }
}
