package tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import tictactoe.TicTacToeAction;
import tictactoe.TicTacToeGame.TokenType;

import org.junit.jupiter.api.Test;

class TicTacToeStateTest {

  @Test
	void testEquality() {
	  TicTacToeState state1 = new TicTacToeState();
		TicTacToeState state2 = new TicTacToeState();
    assertEquals(state1, state2);
	}

  @Test
	void testInequality() {
	  TicTacToeState state1 = new TicTacToeState();
		state1 = (TicTacToeState) state1.applyAction(new TicTacToeAction(0, TicTacToeGame.TokenType.X));

		TicTacToeState state2 = new TicTacToeState();

    assertNotEquals(state1, state2);
	}
	
  @Test
	void testHashCodeEquality() {
	  TicTacToeState state1 = new TicTacToeState();
		TicTacToeState state2 = new TicTacToeState();
    assertEquals(state1.hashCode(), state2.hashCode());
	}

  @Test
	void testHashCodeInequality() {
	  TicTacToeState state1 = new TicTacToeState();
		state1 = (TicTacToeState) state1.applyAction(new TicTacToeAction(0, TicTacToeGame.TokenType.X));

		TicTacToeState state2 = new TicTacToeState();

    assertNotEquals(state1.hashCode(), state2.hashCode());
	}
}
