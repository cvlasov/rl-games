package com.games.tictactoe;

import java.util.ArrayList;
import java.util.List;

public final class TicTacToeHelper {

  private TicTacToeHelper() {}  // restrict instantiation

  /**
   * Type of game being played, in terms of how state symmetry is implemented,
   * if at all.
   */
  enum GameType {
    NORMAL,
    LIMITED_ACTIONS,
    SYMMETRIC_EQUALITY
  }

  /** Contents of a cell on the board. */
  enum TokenType {
    X ("X"),
    O ("O"),
    NONE (" ");

    private final String name;

    TokenType(String s) {
      name = s;
    }

    public String toString() {
      return this.name;
    }
  }

  /** Winner of the game, if any. */
  enum Winner {
    X,
    O,
    DRAW,
    GAME_NOT_OVER
  }

  /** Number of cells in a Tic-Tac-Toe grid. */
  public static final int GRID_SIZE = 9;

  /** Return for an action that causes the game to end and results in a win. */
  public static final int WIN_RETURN = 1;

  /** Return for an action that causes the game to end and results in a loss. */
  public static final int LOSS_RETURN = -1;

  /** Return for an action that causes the game to end and results in a draw. */
  public static final int DRAW_RETURN = 0;

  /** Return for an action that does not cause the game to end. */
  public static final int GAME_IN_PROGRESS_RETURN = 0;

  public static List<TokenType> flipGridVertically(List<TokenType> g) {
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

  public static List<TokenType> flipGridHorizontally(List<TokenType> g) {
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

  public static List<TokenType> flipGridAlongMajorDiagonal(List<TokenType> g) {
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

  public static List<TokenType> flipGridAlongMinorDiagonal(List<TokenType> g) {
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
