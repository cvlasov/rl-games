package com.games.tictactoe;

import com.games.general.Agent;

/**
 * Game of Tic-Tac-Toe where the the first player is chosen randomly. The first
 * player always uses X tokens and the second player always uses O tokens.
 * The symmetry of states is eliminated by the overriden {@code equals()} and
 * {@code hashCode()} functions in {@link TicTacToeStateWithSymmetricEquality}.
 */
public final class TicTacToeGameWithSymmetricEquality extends TicTacToeGame {

  public TicTacToeGameWithSymmetricEquality(Agent a1, Agent a2) {
    super(a1, a2);
    state = new TicTacToeStateWithSymmetricEquality();
  }
}
