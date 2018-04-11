package com.games.nim;

import static com.games.nim.NimHelper.NUM_PILES;
import static com.games.nim.NimHelper.TOKENS_PER_PILE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.games.general.Action;
import com.games.nim.NimHelper.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class NimStateTest {

  @Test
	public void testEquality() {
	  NimState state1 = new NimState();
		NimState state2 = new NimState();
    assertThat(state1, equalTo(state2));
	}

  @Test
  public void testInequalityWithSamePilesAndDifferentNextTurn() {
    int[] piles = new int[NUM_PILES];
    Arrays.fill(piles, TOKENS_PER_PILE);

    NimState state1 = new NimState(Player.X, piles);
    NimState state2 = new NimState(Player.O, piles);
    assertThat(state1, not(equalTo(state2)));
  }

  @Test
  public void testInequalityWithDifferentPilesAndDifferentNextTurn() {
    NimState state1 = new NimState();
    NimState state2 = new NimState(Player.O, new int[NUM_PILES]);
    assertThat(state1, not(equalTo(state2)));
	}

  @Test
  public void testInequalityWithDifferentPilesAndSameNextTurn() {
    NimState state1 = new NimState();
    NimState state2 = new NimState(Player.X, new int[NUM_PILES]);
    assertThat(state1, not(equalTo(state2)));
  }

  @Test
  public void testIsTerminalStateIsTrueForXWin() {
    NimState state = new NimState(Player.X, new int[NUM_PILES]);
    assertTrue(state.isTerminalState());
  }

  @Test
  public void testIsTerminalStateIsTrueForOWin() {
    NimState state = new NimState(Player.O, new int[NUM_PILES]);
    assertTrue(state.isTerminalState());
  }

  @Test
  public void testIsTerminalStateIsFalseForGameWithoutAllPilesEmpty() {
    int[] piles = new int[NUM_PILES];
    piles[0] = TOKENS_PER_PILE;

    NimState state = new NimState(Player.X, piles);
    assertFalse(state.isTerminalState());
  }

  @Test
  public void testAvailableActionsAtBeginningOfGame() {
    int[] piles = new int[] { 3, 3 };

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(new NimAction(0, 3));
    expectedActions.add(new NimAction(0, 2));
    expectedActions.add(new NimAction(0, 1));
    expectedActions.add(new NimAction(1, 3));
    expectedActions.add(new NimAction(1, 2));
    expectedActions.add(new NimAction(1, 1));

    checkSameActions(expectedActions, piles, Player.X);
  }

  @Test
  public void testAvailableActionsWhenSomePilesEmpty() {
    int[] piles = new int[] { 2, 0, 1 };

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(new NimAction(0, 2));
    expectedActions.add(new NimAction(0, 1));
    expectedActions.add(new NimAction(2, 1));

    checkSameActions(expectedActions, piles, Player.X);
  }

  @Test
  public void testAvailableActionsWhenAllPilesEmpty() {
    int[] piles = new int[NUM_PILES];
    Set<Action> expectedActions = new HashSet<>();
    checkSameActions(expectedActions, piles, Player.X);
  }

  private void checkSameActions(
      Set<Action> expectedActions, int[] piles, Player nextPlayer) {
    NimState state = new NimState(nextPlayer, piles);
    Set<Action> actualActions = new HashSet<>(state.getActions());
    assertThat(actualActions, equalTo(expectedActions));
  }
}
