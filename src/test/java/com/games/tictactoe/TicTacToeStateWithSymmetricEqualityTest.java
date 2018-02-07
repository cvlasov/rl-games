package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TicTacToeStateWithSymmetricEqualityTest {

  public TicTacToeStateWithSymmetricEqualityTest() {}

  @Test
  public void testVerticalSymmetryEquality() {
    /*
     * Testing equality between:
     *    | X |           X |   |
     * -----------       -----------
     *  O |   |     and   O |   |
     * -----------       -----------
     *  X |   |             | X |
     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid1.set(1, TokenType.X);
    grid1.set(3, TokenType.O);
    grid1.set(6, TokenType.X);

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(0, TokenType.X);
    grid2.set(3, TokenType.O);
    grid2.set(7, TokenType.X);

    checkEquality(grid1, grid2);
  }

  @Test
  public void testHorizontalSymmetryEquality() {
    /*
     * Testing equality between:
     *    | X |             | X |
     * -----------       -----------
     *  O |   |     and     |   | O
     * -----------       -----------
     *  X |   |             |   | X
     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid1.set(1, TokenType.X);
    grid1.set(3, TokenType.O);
    grid1.set(6, TokenType.X);

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(1, TokenType.X);
    grid2.set(5, TokenType.O);
    grid2.set(8, TokenType.X);

    checkEquality(grid1, grid2);
  }

  @Test
  public void testVerticalAndHorizontalSymmetryEquality() {
    /*
     * Testing equality between:
     *    | X |             |   | X
     * -----------       -----------
     *  O |   |     and     |   | O
     * -----------       -----------
     *  X |   |             | X |
     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid1.set(1, TokenType.X);
    grid1.set(3, TokenType.O);
    grid1.set(6, TokenType.X);

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(2, TokenType.X);
    grid2.set(5, TokenType.O);
    grid2.set(7, TokenType.X);

    checkEquality(grid1, grid2);
  }

  @Test
  public void testMajorDiagonalSymmetryEquality() {
    /*
     * Testing equality between:
     *    | X |             | O | X
     * -----------       -----------
     *  O |   |     and   X |   |
     * -----------       -----------
     *  X |   |             |   |
     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid1.set(1, TokenType.X);
    grid1.set(3, TokenType.O);
    grid1.set(6, TokenType.X);

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(3, TokenType.X);
    grid2.set(1, TokenType.O);
    grid2.set(2, TokenType.X);

    checkEquality(grid1, grid2);
  }

  @Test
  public void testMinorDiagonalSymmetryEquality() {
    /*
     * Testing equality between:
     *    | X |             |   |
     * -----------       -----------
     *  O |   |     and     |   | X
     * -----------       -----------
     *  X |   |           X | O |
     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid1.set(1, TokenType.X);
    grid1.set(3, TokenType.O);
    grid1.set(6, TokenType.X);

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(5, TokenType.X);
    grid2.set(7, TokenType.O);
    grid2.set(6, TokenType.X);

    checkEquality(grid1, grid2);
  }

  @Test
  public void testLackOfSymmetryInequality1() {
    /*
     * Testing inequality between:
     *    | X |             |   |
     * -----------       -----------
     *  O |   |     and   O | x |
     * -----------       -----------
     *  X |   |           X |   |
     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid1.set(1, TokenType.X);
    grid1.set(3, TokenType.O);
    grid1.set(6, TokenType.X);

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(4, TokenType.X);
    grid2.set(3, TokenType.O);
    grid2.set(6, TokenType.X);

    checkInequality(grid1, grid2);
  }

  @Test
  public void testLackOfSymmetryInequality2() {
    /*
     * Testing inequality between:
     *    |   |             |   |
     * -----------       -----------
     *    |   |     and     | x |
     * -----------       -----------
     *    |   |             |   |
     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(4, TokenType.X);

    checkInequality(grid1, grid2);
  }

  @Test
  public void testLackOfSymmetryInequality3() {
    /*
     * Testing inequality between:
     *    |   |           X | X | X
     * -----------       -----------
     *    |   |     and   O | x | O
     * -----------       -----------
     *    |   |           X | O | O

     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(0, TokenType.X);
    grid2.set(1, TokenType.X);
    grid2.set(2, TokenType.X);
    grid2.set(3, TokenType.O);
    grid2.set(4, TokenType.X);
    grid2.set(5, TokenType.O);
    grid2.set(6, TokenType.X);
    grid2.set(7, TokenType.O);
    grid2.set(8, TokenType.O);

    checkInequality(grid1, grid2);
  }

  private void checkEquality(List<TokenType> grid1, List<TokenType> grid2) {
    TicTacToeStateWithSymmetricEquality state1 =
        new TicTacToeStateWithSymmetricEquality(grid1);

    TicTacToeStateWithSymmetricEquality state2 =
        new TicTacToeStateWithSymmetricEquality(grid2);

    assertThat(state1, equalTo(state2));
  }

  private void checkInequality(List<TokenType> grid1, List<TokenType> grid2) {
    TicTacToeStateWithSymmetricEquality state1 =
        new TicTacToeStateWithSymmetricEquality(grid1);

    TicTacToeStateWithSymmetricEquality state2 =
        new TicTacToeStateWithSymmetricEquality(grid2);

    assertThat(state1, not(equalTo(state2)));
  }
}
