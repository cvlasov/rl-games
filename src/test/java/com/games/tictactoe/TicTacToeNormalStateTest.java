package com.games.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.games.tictactoe.TicTacToeHelper.TokenType;

import org.junit.Test;

public class TicTacToeNormalStateTest {

  public TicTacToeNormalStateTest() {}

  @Test
	public void testEquality() {
	  TicTacToeNormalState state1 = new TicTacToeNormalState();
		TicTacToeNormalState state2 = new TicTacToeNormalState();
    assertEquals(state1, state2);
	}

  @Test
  public void testInequality() {
	  TicTacToeNormalState state1 = new TicTacToeNormalState();
		state1 = (TicTacToeNormalState) state1.applyAction(new TicTacToeAction(0, TokenType.X));

		TicTacToeNormalState state2 = new TicTacToeNormalState();

    assertNotEquals(state1, state2);
	}

  @Test
	public void testHashCodeEquality() {
	  TicTacToeNormalState state1 = new TicTacToeNormalState();
		TicTacToeNormalState state2 = new TicTacToeNormalState();
    assertEquals(state1.hashCode(), state2.hashCode());
	}

  @Test
	public void testHashCodeInequality() {
	  TicTacToeNormalState state1 = new TicTacToeNormalState();
		state1 = (TicTacToeNormalState) state1.applyAction(new TicTacToeAction(0, TokenType.X));

		TicTacToeNormalState state2 = new TicTacToeNormalState();

    assertNotEquals(state1.hashCode(), state2.hashCode());
	}
}
