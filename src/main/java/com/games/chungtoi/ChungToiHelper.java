package com.games.chungtoi;

import java.util.List;

public class ChungToiHelper {

  private ChungToiHelper() {}  // restrict instantiation

  /** Type of action. */
  enum ActionType {
    PUT,
    ROTATE,
    MOVE_AND_ROTATE,
    PASS
  }

  /** Type of token in a cell on the board. */
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

  /** Orientation of a token on the board. */
  enum TokenOrientation {
    CARDINAL,
    DIAGONAL
  }

  /** Winner of the game, if any. */
  public enum Winner {
    X,
    O,
    DRAW,
    GAME_NOT_OVER
  }

  /** Number of cells in a Chung Toi grid. */
  public static final int GRID_SIZE = 9;

  /** Return for an action that causes the game to end and results in a win. */
  public static final int WIN_RETURN = 1;

  /** Return for an action that causes the game to end and results in a loss. */
  public static final int LOSS_RETURN = -1;

  /** Return for an action that causes the game to end and results in a draw. */
  public static final int DRAW_RETURN = 0;

  /** Return for an action that does not cause the game to end. */
  public static final int GAME_IN_PROGRESS_RETURN = 0;

  public static String getBoardIndexName(int index) {
    switch (index) {
      case 0 : return "top-left";
      case 1 : return "top-middle";
      case 2 : return "top-right";
      case 3 : return "middle-left";
      case 4 : return "middle-middle";
      case 5 : return "middle-right";
      case 6 : return "bottom-left";
      case 7 : return "bottom-middle";
      case 8 : return "bottom-right";
      default: break;
    }

    return null;
  }

  public static void print(List<ChungToiToken> grid) {
    StringBuilder builder = new StringBuilder();
    appendToken(builder, grid.get(0), false);
    appendToken(builder, grid.get(1), false);
    appendToken(builder, grid.get(2), true);
    builder.append("***********\n");
    appendToken(builder, grid.get(3), false);
    appendToken(builder, grid.get(4), false);
    appendToken(builder, grid.get(5), true);
    builder.append("***********\n");
    appendToken(builder, grid.get(6), false);
    appendToken(builder, grid.get(7), false);
    appendToken(builder, grid.get(8), true);
    System.out.println(builder.toString());
  }

  private static void appendToken(
      StringBuilder builder, ChungToiToken token, boolean endOfLine) {

    if (token.type == TokenType.NONE) {
      builder.append("   ");
    } else {
      switch(token.orientation) {
        case CARDINAL: builder.append("-" + token.type + "-"); break;
        case DIAGONAL: builder.append("|" + token.type + "|"); break;
      }
    }

    builder.append(endOfLine ? "\n" : "*");
  }
}
