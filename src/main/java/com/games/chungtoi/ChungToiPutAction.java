package com.games.chungtoi;

import com.games.chungtoi.ChungToiHelper.TokenType;
import com.games.general.Action;

public class ChungToiPutAction implements Action {

  /** Token to put on the board. */
  public final TokenType tokenType;

  /** Place on the board to put the token. */
  public final int index;

  public ChungToiPutAction(TokenType type, int index) {
    this.tokenType = type;
    this.index = index;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!ChungToiPutAction.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final ChungToiPutAction other = (ChungToiPutAction) o;
    return this.tokenType == other.tokenType && this.index == other.index;
  }

  @Override
  public int hashCode() {
    int hash = 2^index;

    switch (tokenType) {
      case X_NORMAL:   hash *= 3; break;
      case X_DIAGONAL: hash *= 5; break;
      case O_NORMAL:   hash *= 7; break;
      case O_DIAGONAL: hash *= 11; break;
      case NONE:       hash *= 13; break;
    }

    return hash;
  }

  @Override
  public void print() {
    StringBuilder builder = new StringBuilder();
    builder.append(tokenType.toString() + " in "
                   + ChungToiHelper.getBoardIndexName(index));
    System.out.print(builder.toString());
  }
}
