package com.games.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.games.general.State;
//import general.Action;
//import tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeGame.TokenType;
//
//import java.util.Arrays;
//import java.util.Set;
//import java.util.HashSet;
//
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeStateWithLimitedActionsTest {
  
  @Test
	void testEquality() {
	  TicTacToeStateWithLimitedActions state1 =
        new TicTacToeStateWithLimitedActions(new TokenType[9]);
		TicTacToeState state2 = new TicTacToeState();
    //assertEquals(state1, state2);
	}
  
  //@Test
  //void testAvailableActionsOnEmptyGrid() {
  //  TicTacToeStateWithLimitedActions state = new TicTacToeStateWithLimitedActions();
  //  assertTrue(true);
  //  //System.out.println("initializing");
  //  //state = new TicTacToeStateWithLimitedActions();
  //  //System.out.println("DONE initializing");
  //  //System.out.println("running first test");
  //  ///*
  //  // * Possible actions on empty grid:
  //  // *  * | * |
  //  // * -----------
  //  // *    | * |
  //  // * -----------
  //  // *    |   |
  //  // */
  //  //Set<Action> actions = new HashSet<>();
  //  //actions.add(new TicTacToeAction(0, TokenType.X));
  //  //actions.add(new TicTacToeAction(1, TokenType.X));
  //  //actions.add(new TicTacToeAction(4, TokenType.X));
  //  //
  //  //if (state == null) {
  //  //  System.out.println("state is null");
  //  //}
  //  //
  //  //Set<Action> actualActions = new HashSet<>(state.getActions());
  //  //
  //  //assertEquals(actions, actualActions);
  //}
}
