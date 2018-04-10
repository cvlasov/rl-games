package com.games.nim;

import com.games.general.Action;

public class NimAction implements Action {

  /** Pile from which tokens will be taken. */
  public final int pile;

  /** Number of tokens to be taken from the pile. */
  public final int numTokens;

  public NimAction(int pile, int numTokens) {
    this.pile = pile;
    this.numTokens = numTokens;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!NimAction.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final NimAction other = (NimAction) o;
    return this.pile == other.pile && this.numTokens == other.numTokens;
  }

  @Override
  public int hashCode() {
    return 2^pile * 3^numTokens;
  }

  @Override
  public void print() {
    System.out.print(
        String.format("%d token(s) from pile %d", numTokens, pile));
  }
}
