package com.games.chungtoi;

import static com.games.chungtoi.ChungToiHelper.GRID_SIZE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.games.general.Action;
import com.games.chungtoi.ChungToiHelper.Player;
import com.games.chungtoi.ChungToiHelper.TokenType;
import com.games.chungtoi.ChungToiMoveAction;
import com.games.chungtoi.ChungToiPassAction;
import com.games.chungtoi.ChungToiPutAction;
import com.games.chungtoi.ChungToiState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ChungToiStateTest {

  @Test
	public void testEquality() {
	  ChungToiState state1 = new ChungToiState();
		ChungToiState state2 = new ChungToiState();
    assertThat(state1, equalTo(state2));
	}

  @Test
  public void testInequality() {
    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(0, TokenType.X_NORMAL);

	  ChungToiState state1 = new ChungToiState(grid, Player.O);
		ChungToiState state2 = new ChungToiState();
    assertThat(state1, not(equalTo(state2)));
	}

  @Test
  public void testIsTerminalStateIsTrueForXWin() {
    /*
     * This is a terminal state since X won:
     *      *     * /X/
     * ****************
     *  /O/ * -X- * -O-
     * ****************
     *  -X- * /O/ *
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(2, TokenType.X_DIAGONAL);
    grid.set(3, TokenType.O_DIAGONAL);
    grid.set(4, TokenType.X_NORMAL);
    grid.set(5, TokenType.O_NORMAL);
    grid.set(6, TokenType.X_NORMAL);
    grid.set(7, TokenType.O_DIAGONAL);

    ChungToiState state = new ChungToiState(grid, Player.O);
    assertTrue(state.isTerminalState());
  }

  @Test
  public void testIsTerminalStateIsTrueForOWin() {
    /*
     * This is a terminal state since O won:
     *      *     * /X/
     * ****************
     *  /O/ * -O- * -O-
     * ****************
     *  -X- * /X/ *
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(2, TokenType.X_DIAGONAL);
    grid.set(3, TokenType.O_DIAGONAL);
    grid.set(4, TokenType.O_NORMAL);
    grid.set(5, TokenType.O_NORMAL);
    grid.set(6, TokenType.X_NORMAL);
    grid.set(7, TokenType.X_DIAGONAL);

    ChungToiState state = new ChungToiState(grid, Player.X);
    assertTrue(state.isTerminalState());
  }

  @Test
  public void testIsTerminalStateIsFalseForGameWithoutAllTokens() {
    /*
     * This is NOT a terminal state:
     *      *     * /X/
     * ****************
     *      * -O- *
     * ****************
     *      * /X/ *
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(2, TokenType.X_DIAGONAL);
    grid.set(4, TokenType.O_NORMAL);
    grid.set(7, TokenType.X_DIAGONAL);

    ChungToiState state = new ChungToiState(grid, Player.O);
    assertFalse(state.isTerminalState());
  }

  @Test
  public void testIsTerminalStateIsFalseForGameWithoutNoThreeInARow() {
    /*
     * This is NOT a terminal state:
     *      *     * /X/
     * ****************
     *  /X/ * -O- * -O-
     * ****************
     *  -O- * /X/ *
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(2, TokenType.X_DIAGONAL);
    grid.set(3, TokenType.X_DIAGONAL);
    grid.set(4, TokenType.O_NORMAL);
    grid.set(5, TokenType.O_NORMAL);
    grid.set(6, TokenType.O_NORMAL);
    grid.set(7, TokenType.X_DIAGONAL);

    ChungToiState state = new ChungToiState(grid, Player.X);
    assertFalse(state.isTerminalState());
  }

  @Test
  public void testAvailableActionsOnEmptyGrid() {
    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(ChungToiPassAction.getInstance());
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 0));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 0));
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 1));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 1));
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 2));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 2));
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 3));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 3));
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 4));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 4));
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 5));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 5));
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 6));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 6));
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 7));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 7));
    expectedActions.add(new ChungToiPutAction(TokenType.X_NORMAL, 8));
    expectedActions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, 8));

    checkSameActions(expectedActions, grid, Player.X);
  }

  @Test
  public void testAvailableActionsOnGridWithoutAllPieces() {
    /*
     * Possible actions on the following grid without all six pieces:
     *      *     * /X/
     * ****************
     *      * -O- *
     * ****************
     *      * /X/ *
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(2, TokenType.X_DIAGONAL);
    grid.set(4, TokenType.O_NORMAL);
    grid.set(7, TokenType.X_DIAGONAL);

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(ChungToiPassAction.getInstance());
    expectedActions.add(new ChungToiPutAction(TokenType.O_NORMAL, 0));
    expectedActions.add(new ChungToiPutAction(TokenType.O_DIAGONAL, 0));
    expectedActions.add(new ChungToiPutAction(TokenType.O_NORMAL, 1));
    expectedActions.add(new ChungToiPutAction(TokenType.O_DIAGONAL, 1));
    expectedActions.add(new ChungToiPutAction(TokenType.O_NORMAL, 3));
    expectedActions.add(new ChungToiPutAction(TokenType.O_DIAGONAL, 3));
    expectedActions.add(new ChungToiPutAction(TokenType.O_NORMAL, 5));
    expectedActions.add(new ChungToiPutAction(TokenType.O_DIAGONAL, 5));
    expectedActions.add(new ChungToiPutAction(TokenType.O_NORMAL, 6));
    expectedActions.add(new ChungToiPutAction(TokenType.O_DIAGONAL, 6));
    expectedActions.add(new ChungToiPutAction(TokenType.O_NORMAL, 8));
    expectedActions.add(new ChungToiPutAction(TokenType.O_DIAGONAL, 8));

    checkSameActions(expectedActions, grid, Player.O);
  }

  @Test
  public void testAvailableActionsOnGridWithAllPieces() {
    /*
     * Possible actions on the following grid without all six pieces:
     *      *     * -X-
     * ****************
     *  /X/ * -O- * -O-
     * ****************
     *  -O- * /X/ *
     */

    List<TokenType> grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid.set(2, TokenType.X_NORMAL);
    grid.set(3, TokenType.X_DIAGONAL);
    grid.set(4, TokenType.O_NORMAL);
    grid.set(5, TokenType.O_NORMAL);
    grid.set(6, TokenType.O_NORMAL);
    grid.set(7, TokenType.X_DIAGONAL);

    Set<Action> expectedActions = new HashSet<>();
    expectedActions.add(ChungToiPassAction.getInstance());
    expectedActions.add(new ChungToiMoveAction(2, 2, true));
    expectedActions.add(new ChungToiMoveAction(2, 1, false));
    expectedActions.add(new ChungToiMoveAction(2, 1, true));
    expectedActions.add(new ChungToiMoveAction(2, 0, false));
    expectedActions.add(new ChungToiMoveAction(2, 0, true));
    expectedActions.add(new ChungToiMoveAction(3, 3, true));
    expectedActions.add(new ChungToiMoveAction(3, 1, false));
    expectedActions.add(new ChungToiMoveAction(3, 1, true));
    expectedActions.add(new ChungToiMoveAction(7, 7, true));

    checkSameActions(expectedActions, grid, Player.X);
  }

  private void checkSameActions(
      Set<Action> expectedActions, List<TokenType> grid, Player nextPlayer) {
    ChungToiState state = new ChungToiState(grid, nextPlayer);
    Set<Action> actualActions = new HashSet<>(state.getActions());
    assertThat(actualActions, equalTo(expectedActions));
  }
}
