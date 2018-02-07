package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeHelper.TokenType;

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

      List<TokenType> verticalFlip = getVerticalFlipGrid(nextGridState);
      List<TokenType> horizontalFlip = getHorizontalFlipGrid(nextGridState);
      List<TokenType> doubleFlip = getHorizontalFlipGrid(verticalFlip);
      List<TokenType> majorDiagonalFlip =
          getMajorDiagonalFlipGrid(nextGridState);
      List<TokenType> minorDiagonalFlip =
          getMinorDiagonalFlipGrid(nextGridState);

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

  private List<TokenType> getVerticalFlipGrid(List<TokenType> g) {
    List<TokenType> verticalFlipGrid = new ArrayList<>();
    verticalFlipGrid.add(g.get(6));
    verticalFlipGrid.add(g.get(7));
    verticalFlipGrid.add(g.get(8));
    verticalFlipGrid.add(g.get(3));
    verticalFlipGrid.add(g.get(4));
    verticalFlipGrid.add(g.get(5));
    verticalFlipGrid.add(g.get(0));
    verticalFlipGrid.add(g.get(1));
    verticalFlipGrid.add(g.get(2));
    return verticalFlipGrid;
  }

  private List<TokenType> getHorizontalFlipGrid(List<TokenType> g) {
    List<TokenType> horizontalFlipGrid = new ArrayList<>(GRID_SIZE);
    horizontalFlipGrid.add(g.get(2));
    horizontalFlipGrid.add(g.get(1));
    horizontalFlipGrid.add(g.get(0));
    horizontalFlipGrid.add(g.get(5));
    horizontalFlipGrid.add(g.get(4));
    horizontalFlipGrid.add(g.get(3));
    horizontalFlipGrid.add(g.get(8));
    horizontalFlipGrid.add(g.get(7));
    horizontalFlipGrid.add(g.get(6));
    return horizontalFlipGrid;
  }

  private List<TokenType> getMajorDiagonalFlipGrid(List<TokenType> g) {
    List<TokenType> majorDiagonalFlipGrid = new ArrayList<>(GRID_SIZE);
    majorDiagonalFlipGrid.add(g.get(0));
    majorDiagonalFlipGrid.add(g.get(3));
    majorDiagonalFlipGrid.add(g.get(6));
    majorDiagonalFlipGrid.add(g.get(1));
    majorDiagonalFlipGrid.add(g.get(4));
    majorDiagonalFlipGrid.add(g.get(7));
    majorDiagonalFlipGrid.add(g.get(2));
    majorDiagonalFlipGrid.add(g.get(5));
    majorDiagonalFlipGrid.add(g.get(8));
    return majorDiagonalFlipGrid;
  }

  private List<TokenType> getMinorDiagonalFlipGrid(List<TokenType> g) {
    List<TokenType> minorDiagonalFlipGrid = new ArrayList<>(GRID_SIZE);
    minorDiagonalFlipGrid.add(g.get(8));
    minorDiagonalFlipGrid.add(g.get(5));
    minorDiagonalFlipGrid.add(g.get(2));
    minorDiagonalFlipGrid.add(g.get(7));
    minorDiagonalFlipGrid.add(g.get(4));
    minorDiagonalFlipGrid.add(g.get(1));
    minorDiagonalFlipGrid.add(g.get(6));
    minorDiagonalFlipGrid.add(g.get(3));
    minorDiagonalFlipGrid.add(g.get(0));
    return minorDiagonalFlipGrid;
  }
}
