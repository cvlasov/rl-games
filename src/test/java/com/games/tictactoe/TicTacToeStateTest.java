package com.games.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeGame.TokenType;

import org.junit.Test;

public class TicTacToeStateTest {

  public TicTacToeStateTest() {}

  @Test
	public void testEquality() {
	  TicTacToeState state1 = new TicTacToeState();
		TicTacToeState state2 = new TicTacToeState();
    assertEquals(state1, state2);
	}

  @Test
  public void testInequality() {
	  TicTacToeState state1 = new TicTacToeState();
		state1 = (TicTacToeState) state1.applyAction(new TicTacToeAction(0, TokenType.X));

		TicTacToeState state2 = new TicTacToeState();

    assertNotEquals(state1, state2);
	}

  @Test
	public void testHashCodeEquality() {
	  TicTacToeState state1 = new TicTacToeState();
		TicTacToeState state2 = new TicTacToeState();
    assertEquals(state1.hashCode(), state2.hashCode());
	}

  @Test
	public void testHashCodeInequality() {
	  TicTacToeState state1 = new TicTacToeState();
		state1 = (TicTacToeState) state1.applyAction(new TicTacToeAction(0, TokenType.X));

		TicTacToeState state2 = new TicTacToeState();

    assertNotEquals(state1.hashCode(), state2.hashCode());
	}
}
