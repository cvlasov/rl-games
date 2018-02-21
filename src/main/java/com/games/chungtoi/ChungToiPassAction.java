package com.games.chungtoi;

import com.games.general.Action;

public class ChungToiPassAction implements Action {

  /** Place on the board to put the token. */
  private static ChungToiPassAction passActionInstance;

  private ChungToiPassAction() {} // prevent instantiation

  public static ChungToiPassAction getInstance() {
    if (passActionInstance == null) {
      passActionInstance = new ChungToiPassAction();
    }

    return passActionInstance;
  }

  @Override
  public void print() {
    System.out.print("pass");
  }
}
