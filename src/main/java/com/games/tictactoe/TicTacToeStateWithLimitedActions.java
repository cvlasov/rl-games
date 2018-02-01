package com.games.tictactoe;

import com.games.general.Action;
import com.games.general.State;
import com.games.tictactoe.TicTacToeGame.TokenType;
import com.games.tictactoe.TicTacToeGame.Winner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** State of a game of Tic-Tac-Toe. */
public class TicTacToeStateWithLimitedActions implements State {

  /** Number of cells in a Tic-Tac-Toe grid. */
  public final int gridSize = 9;

  /**
   * Tic-Tac-Toe grid, where the array indices represent the board as:
   *    0 | 1 | 2
   *   -----------
   *    3 | 4 | 5
   *   -----------
   *    6 | 7 | 8
   */
  private final TokenType[] grid;

  /** List of possible actions to take from this state. */
  protected List<Action> actions = null;
  
  /**
   * Winning token type (or draw) if this is a terminal state, otherwise
   * {@link tictactoe.TicTacToeGame#Winner.GAME_NOT_OVER}.
   */
  protected Winner winner = Winner.GAME_NOT_OVER;
  
  public TokenType[] verticalFlipGrid;
  public TokenType[] horizontalFlipGrid;

  /** Creates a state with an empty grid. */
  public TicTacToeStateWithLimitedActions() {
    grid = new TokenType[gridSize];
    Arrays.fill(grid, TokenType.NONE); // initializes empty grid
    computeActions();
  }
  
  /**
   * Creates the state that results from applying the given action at the given
   * state.
   */
  protected TicTacToeStateWithLimitedActions(TicTacToeStateWithLimitedActions oldState,
                                           TicTacToeAction action) {
    grid = new TokenType[gridSize];
    
    for (int i = 0 ; i < gridSize ; i++) {
      if (i == action.index) {
        grid[i] = action.tokenType;
      } else {
        grid[i] = oldState.grid[i];
      }
    }

    computeActions();
  }

  public TicTacToeStateWithLimitedActions(TokenType[] g) {
    grid = new TokenType[gridSize];
    for (int i = 0 ; i < gridSize ; i++) {
      grid[i] = g[i];
    }
    computeActions();
  }
  
  @Override
  public List<Action> getActions() {
    return actions;
  }
  
  @Override
  public State applyAction(Action a) {
    return new TicTacToeStateWithLimitedActions(this, (TicTacToeAction) a);
  }
  
  @Override
  public State copy() {
    return new TicTacToeStateWithLimitedActions(this.grid);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!TicTacToeStateWithLimitedActions.class.isAssignableFrom(o.getClass())) {
      return false;
    }
    final TicTacToeStateWithLimitedActions other = (TicTacToeStateWithLimitedActions) o;
    
    for (int i = 0 ; i < gridSize ; i++) {
      if (this.grid[i] != other.grid[i]) {
        return false;
      }
    }
    
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3 * grid[0].hashCode();
    hash +=    5 * grid[1].hashCode();
    hash +=    7 * grid[2].hashCode();
    hash +=   11 * grid[3].hashCode();
    hash +=   13 * grid[4].hashCode();
    hash +=   17 * grid[5].hashCode();
    hash +=   19 * grid[6].hashCode();
    hash +=   23 * grid[7].hashCode();
    hash +=   29 * grid[8].hashCode();
    return hash;
  }
  
  @Override
  public void print() {
    System.out.println(" " + grid[0] + " | " + grid[1] + " | " + grid[2]);
    System.out.println("-----------");
    System.out.println(" " + grid[3] + " | " + grid[4] + " | " + grid[5]);
    System.out.println("-----------");
    System.out.println(" " + grid[6] + " | " + grid[7] + " | " + grid[8]);
    System.out.println();
  }
  
  /**
   * Populates {@link #actions} with all actions that can be taken from this
   * state.
   */
  public void computeActions() {
    // Actions are only computed once
    if (actions != null) return;

    actions = new ArrayList<>();
    
    int diff = 0; // X's turn if diff = 0, O's turn if diff = 1

    for (TokenType t : grid) {
      switch (t) {
        case X:    diff += 1; break;
        case O:    diff -= 1; break;
        case NONE: break;
      }
    }

    TokenType tokenType = diff == 0 ? TokenType.X : TokenType.O;

    List<Action> possibleActions = new ArrayList<>();
    List<TokenType[]> possibleNextStates = new ArrayList<>();
    
    for (int i = 0; i < gridSize ; i++) {
      if (grid[i] == TokenType.NONE) {
        TicTacToeAction action = new TicTacToeAction(i, tokenType);
        TokenType[] nextGridState = new TokenType[gridSize];
        
        for (int j = 0 ; j < gridSize ; j++) {
          nextGridState[j] = grid[j];
        }
        
        nextGridState[action.index] = action.tokenType;
        
        if (possibleNextStates.contains(getVerticalFlipGrid(nextGridState))
            || possibleNextStates.contains(getHorizontalFlipGrid(nextGridState))) {
          continue;
        }
        
        possibleActions.add(action);
        possibleNextStates.add(nextGridState);
      }
    }
    
    actions.addAll(possibleActions);
  }
  
  private TokenType[] getVerticalFlipGrid(TokenType[] g) {
    TokenType[] verticalFlipGrid = g;
    verticalFlipGrid[0] = g[6];
    verticalFlipGrid[1] = g[7];
    verticalFlipGrid[2] = g[8];
    verticalFlipGrid[6] = g[0];
    verticalFlipGrid[7] = g[1];
    verticalFlipGrid[8] = g[2];
    return verticalFlipGrid;
  }
  
  private TokenType[] getHorizontalFlipGrid(TokenType[] g) {
    TokenType[] horizontalFlipGrid = g;
    horizontalFlipGrid[0] = g[2];
    horizontalFlipGrid[3] = g[5];
    horizontalFlipGrid[6] = g[8];
    horizontalFlipGrid[2] = g[0];
    horizontalFlipGrid[5] = g[3];
    horizontalFlipGrid[8] = g[6];
    return horizontalFlipGrid;
  }
  
  /**
   * Returns whether or not this is a terminal state
   * <p>
   * If it is a terminal state, updates {@link #winner} accordingly.
   *
   * @return true if this is a terminal state, false otherwise
   */
  public boolean checkIfTerminalState() {
    if (grid[0] != TokenType.NONE) {
      if (grid[0] == grid[1] && grid[1] == grid[2]
              || grid[0] == grid[4] && grid[4] == grid[8]
              || grid[0] == grid[3] && grid[3] == grid[6]) {
        switch (grid[0]) {
          case X: winner = Winner.X; return true;
          case O: winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }
    if (grid[4] != TokenType.NONE) {
      if (grid[3] == grid[4] && grid[4] == grid[5]
              || grid[1] == grid[4] && grid[4] == grid[7]
              || grid[2] == grid[4] && grid[4] == grid[6]) {
        switch (grid[4]) {
          case X: winner = Winner.X; return true;
          case O: winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }
    if (grid[8] != TokenType.NONE) {
      if (grid[6] == grid[7] && grid[7] == grid[8]
              || grid[2] == grid[5] && grid[5] == grid[8]) {
        switch (grid[8]) {
          case X: winner = Winner.X; return true;
          case O: winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }

    int occupiedSquares = 0;

    for (TokenType t : grid) {
      if (t != TokenType.NONE) {
        occupiedSquares += 1;
      }
    }

    if (occupiedSquares == gridSize) {
      winner = Winner.DRAW;
      return true;
    }

    return false;
  }

  /**
   * Returns the winner of the game, if any.
   * <p>
   * Can only be called after {@link #isTerminalState}.
   *
   * @return winner of the game, if any
   */
  public Winner getWinner() {
    return winner;
  }
}
