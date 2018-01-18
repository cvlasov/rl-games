package tictactoe;

import general.Action;
import general.State;
import tictactoe.TicTacToeGame.TokenType;
import tictactoe.TicTacToeGame.Winner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** State of a Tic-Tac-Toe game. */
public class TicTacToeState implements State {

  private final int gridSize = 9;

  /**
   *  Tic-tac-toe grid, where the array indices represent the board as:
   *     0 | 1 | 2
   *    -----------
   *     3 | 4 | 5
   *    -----------
   *     6 | 7 | 8
   */
  private final TokenType[] grid;

  private final List<Action> actions;
  private Winner winner = Winner.GAME_NOT_OVER;

  /** Empty grid is the only grid that can be initialized by other classes. */
  public TicTacToeState() {
    grid = new TokenType[gridSize];
    Arrays.fill(grid, TokenType.NONE); // initializes empty grid
    
    actions = new ArrayList<>();
    computeActions();
  }

  private TicTacToeState(TicTacToeState oldState) {
    grid = oldState.grid;
    
    actions = new ArrayList<>();
    computeActions();
  }
  
  private TicTacToeState(TicTacToeState oldState, TicTacToeAction a) {
    grid = new TokenType[gridSize];
    
    for (int i = 0 ; i < gridSize ; i++) {
      if (i == a.index) {
        grid[i] = a.tokenType;
      } else {
        grid[i] = oldState.grid[i];
      }
    }

    actions = new ArrayList<>();
    computeActions();
  }

  @Override
  public List<Action> getActions() {
    return actions;
  }
  
  private void computeActions() {
    // Called only once, from the constructor
  
    int diff = 0; // X's turn if diff = 0, O's turn if diff = 1

    for (TokenType t : grid) {
      switch (t) {
        case X: diff += 1; break;
        case O: diff -= 1; break;
        case NONE: break;
      }
    }

    TokenType tokenType = diff == 0 ? TokenType.X : TokenType.O;

    for (int i = 0; i < gridSize ; i++) {
      if (grid[i] == TokenType.NONE) {
        actions.add(new TicTacToeAction(i, tokenType));
      }
    }
  }

  @Override
  public State applyAction(Action a) {
    return new TicTacToeState(this, (TicTacToeAction) a);
  }
  
  @Override
  public State copy() {
    return new TicTacToeState(this);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!TicTacToeState.class.isAssignableFrom(o.getClass())) {
      return false;
    }
    final TicTacToeState other = (TicTacToeState) o;
    
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

  public boolean checkIfTerminalState() {
    if (grid[0] != TokenType.NONE) {
      if (grid[0] == grid[1] && grid[1] == grid[2]
              || grid[0] == grid[4] && grid[4] == grid[8]
              || grid[0] == grid[3] && grid[3] == grid[6]) {
        switch (grid[0]) {
          case X: winner = Winner.X; return true;
          case O: winner = Winner.O; return true;
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
        }
      }
    }
    if (grid[8] != TokenType.NONE) {
      if (grid[6] == grid[7] && grid[7] == grid[8]
              || grid[2] == grid[5] && grid[5] == grid[8]) {
        switch (grid[8]) {
          case X: winner = Winner.X; return true;
          case O: winner = Winner.O; return true;
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

  /** Can only be called after isTerminalState() */
  public Winner getWinner() {
    return winner;
  }

  public void print() {
    System.out.println(" " + grid[0] + " | " + grid[1] + " | " + grid[2]);
    System.out.println("-----------");
    System.out.println(" " + grid[3] + " | " + grid[4] + " | " + grid[5]);
    System.out.println("-----------");
    System.out.println(" " + grid[6] + " | " + grid[7] + " | " + grid[8]);
    System.out.println();
  }
}
