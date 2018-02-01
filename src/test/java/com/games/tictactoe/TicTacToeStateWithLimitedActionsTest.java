package com.games.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.games.general.State;
import com.games.general.Action;
import com.games.tictactoe.TicTacToeAction;
import com.games.tictactoe.TicTacToeGame.TokenType;

import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

public class TicTacToeStateWithLimitedActionsTest {

  public TicTacToeStateWithLimitedActionsTest() {}

  @Test
  public void testAvailableActionsOnEmptyGrid() {
    /*
     * Possible actions on empty grid:
     *  * | * |
     * -----------
     *    | * |
     * -----------
     *    |   |
     */

    TicTacToeStateWithLimitedActions state =
        new TicTacToeStateWithLimitedActions();

    Set<Action> actions = new HashSet<>();
    actions.add(new TicTacToeAction(0, TokenType.X));
    actions.add(new TicTacToeAction(1, TokenType.X));
    actions.add(new TicTacToeAction(4, TokenType.X));

    Set<Action> actualActions = new HashSet<>(state.getActions());

    assertEquals(actions, actualActions);
  }

  @Test
  public void testAvailableActionsOnSemiFilledGrid() {
    /*
     * Possible actions on the following semi-filled grid:
     *  * | X |
     * -----------
     *  * | * |
     * -----------
     *  * | * |
     */

    TicTacToeStateWithLimitedActions state =
        new TicTacToeStateWithLimitedActions();
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(1, TokenType.X));

    Set<Action> actions = new HashSet<>();
    actions.add(new TicTacToeAction(0, TokenType.O));
    actions.add(new TicTacToeAction(3, TokenType.O));
    actions.add(new TicTacToeAction(4, TokenType.O));
    actions.add(new TicTacToeAction(6, TokenType.O));
    actions.add(new TicTacToeAction(7, TokenType.O));

    Set<Action> actualActions = new HashSet<>(state.getActions());

    assertEquals(actions, actualActions);
  }

  @Test
  public void testAvailableActionsOnFullGrid() {
    /*
     * Possible actions on the following full grid:
     *  O | X | X
     * -----------
     *  O | X | O
     * -----------
     *  X | O | X
     */

    TicTacToeStateWithLimitedActions state =
        new TicTacToeStateWithLimitedActions();
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(1, TokenType.X));
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(3, TokenType.O));
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(8, TokenType.X));
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(0, TokenType.O));
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(6, TokenType.X));
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(5, TokenType.O));
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(4, TokenType.X));
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(7, TokenType.O));
    state = (TicTacToeStateWithLimitedActions) state.applyAction(new TicTacToeAction(2, TokenType.X));

    Set<Action> actions = new HashSet<>();
    Set<Action> actualActions = new HashSet<>(state.getActions());

    assertEquals(actions, actualActions);
  }
}
