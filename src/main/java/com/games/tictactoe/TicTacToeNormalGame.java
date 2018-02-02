package com.games.tictactoe;

import com.games.general.Agent;

/**
 * Game of Tic-Tac-Toe where the the first player is chosen randomly. The first
 * player always uses X token and the second player uses O tokens.
 */
public final class TicTacToeNormalGame extends TicTacToeGame {

  public TicTacToeNormalGame(Agent a1, Agent a2) {
    super(a1, a2);
    state = new TicTacToeNormalState();
  }
}
