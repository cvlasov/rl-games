package com.games.tictactoe;

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
}
