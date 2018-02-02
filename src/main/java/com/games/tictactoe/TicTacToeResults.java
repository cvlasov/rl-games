package com.games.tictactoe;

/** Results of playing multiple games of Tic-Tac-Toe. */
public final class TicTacToeResults {

  public final int wins;
  public final int losses;
  public final int draws;

  public TicTacToeResults(int w, int l, int d) {
    wins = w;
    losses = l;
    draws = d;
  }
}
