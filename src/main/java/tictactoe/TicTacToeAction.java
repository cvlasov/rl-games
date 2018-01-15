package tictactoe;

import general.Action;
import tictactoe.TicTacToeGame.TokenType;

public class TicTacToeAction implements Action {

  public final int index;
  public final TokenType tokenType;

  public TicTacToeAction(int i, TokenType t) {
    this.index = i;
    this.tokenType = t;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!TicTacToeAction.class.isAssignableFrom(o.getClass())) {
      return false;
    }
    final TicTacToeAction other = (TicTacToeAction) o;
    return this.index == other.index && this.tokenType == other.tokenType;
  }

  @Override
  public int hashCode() {
    int hash = index;

    switch (tokenType) {
      case NONE: hash += 0; break;
      case X:    hash += 1; break;
      case O:    hash += 2; break;
    }

    return hash;
  }
}
