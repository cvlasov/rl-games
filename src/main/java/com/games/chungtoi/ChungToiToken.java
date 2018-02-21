package com.games.chungtoi;

import com.games.chungtoi.ChungToiHelper.TokenOrientation;
import com.games.chungtoi.ChungToiHelper.TokenType;

public class ChungToiToken {

  public final TokenType type;
  public final TokenOrientation orientation;

  public ChungToiToken(TokenType type, TokenOrientation orientation) {
    this.type = type;
    this.orientation = orientation;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!ChungToiToken.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final ChungToiToken other = (ChungToiToken) o;
    return this.type == other.type && this.orientation == other.orientation;
  }

  @Override
  public int hashCode() {
    int hash = 0;

    switch (type) {
      case X:    hash += 3; break;
      case O:    hash += 3*3; break;
      case NONE: hash += 3*3*3; break;
    }

    switch (orientation) {
      case CARDINAL: hash += 5; break;
      case DIAGONAL: hash += 5*5; break;
    }

    return hash;
  }
}
