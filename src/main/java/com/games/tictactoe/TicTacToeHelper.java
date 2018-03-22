package com.games.tictactoe;

import com.games.general.Action;

import java.util.ArrayList;
import java.util.List;

public final class TicTacToeHelper {

  private TicTacToeHelper() {}  // restrict instantiation

  /**
   * Type of game being played, in terms of how state symmetry is implemented,
   * if at all.
   */
  public enum GameType {
    NORMAL,
    LIMITED_ACTIONS,
    SYMMETRIC_EQUALITY
  }

  public enum Player {
    X,
    O
  }

  /** Contents of a cell on the board. */
  public enum TokenType {
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
  public enum Winner {
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

  public static void print(List<TokenType> grid) {
    System.out.println(" " + grid.get(0) + " | " + grid.get(1) + " | " + grid.get(2));
    System.out.println("-----------");
    System.out.println(" " + grid.get(3) + " | " + grid.get(4) + " | " + grid.get(5));
    System.out.println("-----------");
    System.out.println(" " + grid.get(6) + " | " + grid.get(7) + " | " + grid.get(8));
    System.out.println();
  }

  public static void printActions(TicTacToeState state) {
    System.out.println("Actions available from");
    state.print();

    for (Action a : state.getActions()) {
      a.print();
      System.out.println();
    }
  }

  /**
   * Returns a hash code of a Tic-Tac-Toe grid in such a way that two states
   * have the same hash code if and only if they have the same tokens, or lack
   * thereof, at the exact same indices (i.e. no symmetry is taken into
   * account).
   */
  public static int standardHashCode(List<TokenType> grid) {
    int hash = 3 * grid.get(0).hashCode();
    hash +=    5 * grid.get(1).hashCode();
    hash +=    7 * grid.get(2).hashCode();
    hash +=   11 * grid.get(3).hashCode();
    hash +=   13 * grid.get(4).hashCode();
    hash +=   17 * grid.get(5).hashCode();
    hash +=   19 * grid.get(6).hashCode();
    hash +=   23 * grid.get(7).hashCode();
    hash +=   29 * grid.get(8).hashCode();
    return hash;
  }

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
