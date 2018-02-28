package com.games.chungtoi;

import java.util.List;

public class ChungToiHelper {

  private ChungToiHelper() {}  // restrict instantiation

  /** Type of token in a cell on the board. */
  public enum TokenType {
    X_NORMAL   ("-X-"),
    X_DIAGONAL ("/X/"),
    O_NORMAL   ("-O-"),
    O_DIAGONAL ("/O/"),
    NONE ("   ");

    private final String name;

    TokenType(String s) {
      name = s;
    }

    public String toString() {
      return this.name;
    }
  }

  public enum Player {
    X,
    O
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

  /** Number of tokens each player can put down. */
  public static final int TOKENS_PER_PLAYER = 3;

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

  public static void print(List<TokenType> grid) {
    StringBuilder builder = new StringBuilder();
    builder.append(" " + grid.get(0).toString() + " *");
    builder.append(" " + grid.get(1).toString() + " *");
    builder.append(" " + grid.get(2).toString() + "\n");
    builder.append("*****************\n");
    builder.append(" " + grid.get(3).toString() + " *");
    builder.append(" " + grid.get(4).toString() + " *");
    builder.append(" " + grid.get(5).toString() + "\n");
    builder.append("*****************\n");
    builder.append(" " + grid.get(6).toString() + " *");
    builder.append(" " + grid.get(7).toString() + " *");
    builder.append(" " + grid.get(8).toString() + "\n");
    System.out.println(builder.toString());
  }

  public static boolean threeInARow(
      TokenType type1, TokenType type2, TokenType type3) {
    return samePlayer(type1, type2)
           && samePlayer(type2, type3)
           && samePlayer(type3, type1);
  }

  public static boolean samePlayer(TokenType type1, TokenType type2) {
    switch (type1) {
      case X_NORMAL:   // fall through
      case X_DIAGONAL: return type2 == TokenType.X_NORMAL || type2 == TokenType.X_DIAGONAL;
      case O_NORMAL:   // fall through
      case O_DIAGONAL: return type2 == TokenType.O_NORMAL || type2 == TokenType.O_DIAGONAL;
      case NONE:       return type2 == TokenType.NONE;
    }

    return false;
  }
}
