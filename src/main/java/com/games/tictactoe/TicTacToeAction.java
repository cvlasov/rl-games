package com.games.tictactoe;

import com.games.general.Action;
import com.games.tictactoe.TicTacToeHelper.TokenType;

/** Action taken by a player in a game of Tic-Tac-Toe. */
public final class TicTacToeAction implements Action {

  /** Index on board where the piece is to be placed. */
  public final int index;

  /** Type of token to be placed. */
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

  @Override
  public void print() {
    System.out.print(tokenType.toString() + " in ");
    String s = "";

    switch (index) {
      case 0 : s = "top-left";      break;
      case 1 : s = "top-middle";    break;
      case 2 : s = "top-right";     break;
      case 3 : s = "middle-left";   break;
      case 4 : s = "middle-middle"; break;
      case 5 : s = "middle-right";  break;
      case 6 : s = "bottom-left";   break;
      case 7 : s = "bottom-middle"; break;
      case 8 : s = "bottom-right";  break;
      default: break;
    }

    System.out.print(s);
  }
}
