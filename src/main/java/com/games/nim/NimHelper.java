package com.games.nim;

public class NimHelper {

  /** Number of piles in the game. */
  public static final int NUM_PILES = 3;

  /** Number of tokens in each pile when the game begins. */
  public static final int TOKENS_PER_PILE = 7;

  /** Return for an action that causes the game to end and results in a win. */
  public static final int WIN_RETURN = 1;

  /** Return for an action that causes the game to end and results in a loss. */
  public static final int LOSS_RETURN = -1;

  /** Return for an action that does not cause the game to end. */
  public static final int GAME_IN_PROGRESS_RETURN = 0;

  /** Names of the game players. */
  public enum Player {
    X,
    O
  }

  /** Winner of the game, if any. */
  public enum Winner {
    X,
    O,
    GAME_NOT_OVER
  }
}
