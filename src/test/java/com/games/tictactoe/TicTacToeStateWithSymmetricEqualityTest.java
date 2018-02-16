package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.flipGridVertically;
import static com.games.tictactoe.TicTacToeHelper.flipGridHorizontally;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMajorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMinorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class TicTacToeStateWithSymmetricEqualityTest {

  private TicTacToeStateWithSymmetricEquality originalState;
  private TicTacToeStateWithSymmetricEquality stateFlippedVertically;
  private TicTacToeStateWithSymmetricEquality stateFlippedHorizontally;
  private TicTacToeStateWithSymmetricEquality stateFlippedVerticallyAndHorizontally;
  private TicTacToeStateWithSymmetricEquality stateFlippedAlongMajorDiagonal;
  private TicTacToeStateWithSymmetricEquality stateFlippedAlongMajorDiagonalAndHorizontally;
  private TicTacToeStateWithSymmetricEquality stateFlippedAlongMinorDiagonal;
  private TicTacToeStateWithSymmetricEquality stateFlippedAlongMinorDiagonalAndHorizontally;
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

    stateFlippedVertically =
        new TicTacToeStateWithSymmetricEquality(
            flipGridVertically(originalGrid));

    stateFlippedHorizontally =
        new TicTacToeStateWithSymmetricEquality(
            flipGridHorizontally(originalGrid));

    stateFlippedVerticallyAndHorizontally =
        new TicTacToeStateWithSymmetricEquality(
            flipGridHorizontally(flipGridVertically(originalGrid)));

    stateFlippedAlongMajorDiagonal =
        new TicTacToeStateWithSymmetricEquality(
            flipGridAlongMajorDiagonal(originalGrid));

    stateFlippedAlongMajorDiagonalAndHorizontally =
        new TicTacToeStateWithSymmetricEquality(
            flipGridHorizontally(flipGridAlongMajorDiagonal(originalGrid)));

    stateFlippedAlongMinorDiagonal =
      new TicTacToeStateWithSymmetricEquality(
          flipGridAlongMinorDiagonal(originalGrid));

    stateFlippedAlongMinorDiagonalAndHorizontally =
      new TicTacToeStateWithSymmetricEquality(
          flipGridHorizontally(flipGridAlongMinorDiagonal(originalGrid)));

    emptyState = new TicTacToeStateWithSymmetricEquality();
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
  public void testMajorDiagonalAndHorizontalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedAlongMajorDiagonalAndHorizontally));
  }

  @Test
  public void testMinorDiagonalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedAlongMinorDiagonal));
  }

  @Test
  public void testMinorDiagonalAndHorizontalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedAlongMinorDiagonalAndHorizontally));
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
}
