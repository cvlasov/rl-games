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

  // private List<TokenType> grid;
  // private List<TokenType> gridFlippedVertically;
  // private List<TokenType> gridFlippedHorizontally;
  // private List<TokenType> gridFlippedVerticallyAndHorizontally;
  // private List<TokenType> gridFlippedAlongMajorDiagonal;
  // private List<TokenType> gridFlippedAlongMinorDiagonal;
  // private List<TokenType> emptyGrid;

  private TicTacToeStateWithSymmetricEquality originalState;
  private TicTacToeStateWithSymmetricEquality stateFlippedVertically;
  private TicTacToeStateWithSymmetricEquality stateFlippedHorizontally;
  private TicTacToeStateWithSymmetricEquality stateFlippedVerticallyAndHorizontally;
  private TicTacToeStateWithSymmetricEquality stateFlippedAlongMajorDiagonal;
  private TicTacToeStateWithSymmetricEquality stateFlippedAlongMinorDiagonal;
  private TicTacToeStateWithSymmetricEquality emptyState;

  public TicTacToeStateWithSymmetricEqualityTest() {
    /*
     *    | X |
     * -----------
     *  O |   |
     * -----------
     *  X |   |
     */
    List<TokenType> originalGrid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    originalGrid.set(1, TokenType.X);
    originalGrid.set(3, TokenType.O);
    originalGrid.set(6, TokenType.X);

    originalState = new TicTacToeStateWithSymmetricEquality(originalGrid);

    /*
     *  X |   |
     * -----------
     *  O |   |
     * -----------
     *    | X |
     */
    List<TokenType> gridFlippedVertically =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedVertically.set(0, TokenType.X);
    gridFlippedVertically.set(3, TokenType.O);
    gridFlippedVertically.set(7, TokenType.X);

    stateFlippedVertically =
        new TicTacToeStateWithSymmetricEquality(gridFlippedVertically);

    /*
     *    | X |
     * -----------
     *    |   | O
     * -----------
     *    |   | X
     */
    List<TokenType> gridFlippedHorizontally =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedHorizontally.set(1, TokenType.X);
    gridFlippedHorizontally.set(5, TokenType.O);
    gridFlippedHorizontally.set(8, TokenType.X);

    stateFlippedHorizontally =
        new TicTacToeStateWithSymmetricEquality(gridFlippedHorizontally);

    /*
     *    |   | X
     * -----------
     *    |   | O
     * -----------
     *    | X |
     */
    List<TokenType> gridFlippedVerticallyAndHorizontally =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedVerticallyAndHorizontally.set(2, TokenType.X);
    gridFlippedVerticallyAndHorizontally.set(5, TokenType.O);
    gridFlippedVerticallyAndHorizontally.set(7, TokenType.X);

    stateFlippedVerticallyAndHorizontally =
        new TicTacToeStateWithSymmetricEquality(gridFlippedVerticallyAndHorizontally);

    /*
     *    | O | X
     * -----------
     *  X |   |
     * -----------
     *    |   |
     */
    List<TokenType> gridFlippedAlongMajorDiagonal =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedAlongMajorDiagonal.set(3, TokenType.X);
    gridFlippedAlongMajorDiagonal.set(1, TokenType.O);
    gridFlippedAlongMajorDiagonal.set(2, TokenType.X);

    stateFlippedAlongMajorDiagonal =
        new TicTacToeStateWithSymmetricEquality(gridFlippedAlongMajorDiagonal);

    /*
     *    |   |
     * -----------
     *    |   | X
     * -----------
     *  X | O |
     */
    List<TokenType> gridFlippedAlongMinorDiagonal =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedAlongMinorDiagonal.set(5, TokenType.X);
    gridFlippedAlongMinorDiagonal.set(7, TokenType.O);
    gridFlippedAlongMinorDiagonal.set(6, TokenType.X);

    stateFlippedAlongMinorDiagonal =
        new TicTacToeStateWithSymmetricEquality(gridFlippedAlongMinorDiagonal);

    /*
     *    |   |
     * -----------
     *    |   |
     * -----------
     *    |   |
     */
    List<TokenType> emptyGrid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));

    emptyState = new TicTacToeStateWithSymmetricEquality(emptyGrid);
  }

  @Test
  public void testVerticalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedVertically));
  }

  @Test
  public void testHorizontalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedHorizontally));
  }

  @Test
  public void testVerticalAndHorizontalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedVerticallyAndHorizontally));
  }

  @Test
  public void testMajorDiagonalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedAlongMajorDiagonal));
  }

  @Test
  public void testMinorDiagonalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedAlongMinorDiagonal));
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

    List<TokenType> nonSymmetricalGrid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    nonSymmetricalGrid.set(4, TokenType.X);
    nonSymmetricalGrid.set(3, TokenType.O);
    nonSymmetricalGrid.set(6, TokenType.X);

    TicTacToeStateWithSymmetricEquality nonSymmetricalState =
        new TicTacToeStateWithSymmetricEquality(nonSymmetricalGrid);

    assertThat(originalState, not(equalTo(nonSymmetricalState)));
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

    List<TokenType> nonEmptyGrid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    nonEmptyGrid.set(4, TokenType.X);

    TicTacToeStateWithSymmetricEquality nonEmptyState =
        new TicTacToeStateWithSymmetricEquality(nonEmptyGrid);

    assertThat(emptyState, not(equalTo(nonEmptyState)));
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

    List<TokenType> fullGrid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    fullGrid.set(0, TokenType.X);
    fullGrid.set(1, TokenType.X);
    fullGrid.set(2, TokenType.X);
    fullGrid.set(3, TokenType.O);
    fullGrid.set(4, TokenType.X);
    fullGrid.set(5, TokenType.O);
    fullGrid.set(6, TokenType.X);
    fullGrid.set(7, TokenType.O);
    fullGrid.set(8, TokenType.O);

    TicTacToeStateWithSymmetricEquality fullState =
        new TicTacToeStateWithSymmetricEquality(fullGrid);

    assertThat(emptyState, not(equalTo(fullState)));
  }

  // TODO: implement tests below
  // @Test
  // public void testEqualIdWithVerticalSymmetry() {
  //
  // }
  //
  // @Test
  // public void testEqualIdWithHorizontalSymmetry() {
  //
  // }
  //
  // @Test
  // public void testEqualIdWithVerticalAndHorizontalSymmetry() {
  //
  // }
  //
  // @Test
  // public void testEqualIdWithMainDiagonalSymmetry() {
  //
  // }
  //
  // @Test
  // public void testEqualIdWithMinorDiagonalSymmetry() {
  //
  // }
}
