package com.games.nim;

import static com.games.nim.NimHelper.NUM_PILES;
import static com.games.nim.NimHelper.TOKENS_PER_PILE;

import com.games.nim.NimHelper.Player;
import com.games.nim.NimHelper.Winner;
import com.games.general.Action;
import com.games.general.State;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** State of a game of Nim. */
public class NimState implements State {

  /** Number of tokens in each pile. */
  private final int[] piles;

  /** List of possible actions to take from this state. */
  private List<Action> actions;

  /** Player whose turn it is to move from this state. */
  private Player nextTurn;

  /**
   * Winning player if this is a terminal state, otherwise
   * {@link NimHelper#Winner.GAME_NOT_OVER}. Null if {@link #isTerminalState()}
   * has not yet been called.
   */
  private Winner winner = null;


  // CONSTRUCTORS

  /** Creates a state with all piles full. */
  public NimState() {
    piles = new int[NUM_PILES];
    Arrays.fill(piles, TOKENS_PER_PILE);
    nextTurn = Player.X;
    computeActions();
    isTerminalState();  // ignore result
  }

  /**
   * Creates the state that results from applying the given action at the given
   * state.
   */
  private NimState(NimState oldState, NimAction action) {
    piles = Arrays.copyOf(oldState.piles, oldState.piles.length);
    piles[action.pile] -= action.numTokens;

    switch (oldState.nextTurn) {
      case X: nextTurn = Player.O; break;
      case O: nextTurn = Player.X; break;
    }

    computeActions();
    isTerminalState();  // ignore result
  }

  /** Creates a state with the given pile sizes and next player. */
  @VisibleForTesting
  NimState(Player next, int... pileSizes) {
    piles = pileSizes;
    nextTurn = next;
    computeActions();
    isTerminalState();  // ignore result
  }


  // OVERWRITTEN METHODS FROM OBJECT

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!NimState.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final NimState other = (NimState) o;

    return this.nextTurn == other.nextTurn
           && Arrays.equals(this.piles, other.piles);
  }

  @Override
  public int hashCode() {
    int hash = Arrays.hashCode(piles);

    switch (nextTurn) {
      case X: hash *= 3; break;
      case O: hash *= 5; break;
    }

    return hash;
  }


  // IMPLEMENTATIONS OF STATE INTERFACE METHODS

  @Override
  public State applyAction(Action action) {
    return new NimState(this, (NimAction) action);
  }

  @Override
  public List<Action> getActions() {
    return actions;
  }

  @Override
  public boolean isTerminalState() {
    int totalCount = 0;

    for (int i = 0 ; i < NUM_PILES ; i++) {
      totalCount += piles[i];
    }

    if (totalCount > 0) {
      winner = Winner.GAME_NOT_OVER;
      return false;
    } else if (nextTurn == Player.X) {
      winner = Winner.X;
      return true;
    } else {
      winner = Winner.O;
      return true;
    }
  }

  @Override
  public void print() {
    System.out.println(Arrays.toString(piles));
  }

  /**
   * Returns the winner of the game, if any.
   * <p>
   * Can only be called after {@link #isTerminalState()}.
   *
   * @return winner of the game, if any
   */
  Winner getWinner() {
    return winner;
  }


  // HELPER METHOD

  /**
   * Populates {@link #actions} with all actions that can be taken from this
   * state.
   */
  protected void computeActions() {
    // Actions are only computed once
    if (actions != null) return;

    actions = new ArrayList<>();

    for (int i = 0; i < NUM_PILES ; i++) {
      for (int n = 1 ; n <= piles[i] ; n++) {
        actions.add(new NimAction(i, n));
      }
    }
  }
}
