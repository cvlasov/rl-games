package com.games.chungtoi;

import com.games.general.Action;

public class ChungToiMoveAction implements Action {

  /** Location of token on the board. */
  public final int startIndex;

  /** Location to move the token to. */
  public final int endIndex;

  /** Whether or not to rotate the token after moving it. */
  public final boolean rotateToken;

  public ChungToiMoveAction(int start, int end, boolean rotate) {
    this.startIndex = start;
    this.endIndex = end;
    this.rotateToken = rotate;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!ChungToiMoveAction.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final ChungToiMoveAction other = (ChungToiMoveAction) o;
    return this.startIndex == other.startIndex
           && this.endIndex == other.endIndex
           && this.rotateToken == other.rotateToken;
  }

  @Override
  public int hashCode() {
    return 2^startIndex * 3^endIndex * 5^(rotateToken ? 1 : 0);
  }

  @Override
  public void print() {
    StringBuilder builder = new StringBuilder();

    if (startIndex != endIndex) {
      builder.append("move " + ChungToiHelper.getBoardIndexName(startIndex));
      builder.append(" to " + ChungToiHelper.getBoardIndexName(endIndex));
    }

    if (startIndex != endIndex && rotateToken) {
      builder.append(" and then ");
    }

    if (rotateToken) {
      builder.append("rotate " + ChungToiHelper.getBoardIndexName(endIndex));
    }

    System.out.print(builder.toString());
  }
}
