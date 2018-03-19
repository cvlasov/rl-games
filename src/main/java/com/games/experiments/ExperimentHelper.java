package com.games.experiments;

public final class ExperimentHelper {

  private ExperimentHelper() {}  // restrict instantiation

  enum GameType {
    CHUNG_TOI ("ChungToi"),
    TIC_TAC_TOE_NORMAL ("TicTacToeNormal"),
    TIC_TAC_TOE_LIMITED_ACTIONS ("TicTacToeLimitedActions"),
    TIC_TAC_TOE_SYMMETRIC_EQUALITY ("TicTacToeSymmetricEquality");

    private final String name;

    GameType(String s) {
      name = s;
    }

    public String toString() {
      return this.name;
    }
  }
}
