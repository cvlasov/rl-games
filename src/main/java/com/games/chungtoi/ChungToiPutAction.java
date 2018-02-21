package com.games.chungtoi;

import com.games.general.Action;

public class ChungToiPutAction implements Action {

  /** Token to put on the board. */
  public final ChungToiToken token;

  /** Place on the board to put the token. */
  public final int index;

  public ChungToiPutAction(ChungToiToken token, int index) {
    this.token = token;
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
    return this.token.equals(other.token) && this.index == other.index;
  }

  @Override
  public int hashCode() {
    return index + token.hashCode();
  }

  @Override
  public void print() {
    StringBuilder builder = new StringBuilder();

    switch (token.orientation) {
      case CARDINAL: builder.append("cardinal "); break;
      case DIAGONAL: builder.append("diagonal "); break;
    }

    builder.append(token.type.toString() + " in "
                   + ChungToiHelper.getBoardIndexName(index));

    System.out.print(builder.toString());
  }
}
