package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static com.games.tictactoe.TicTacToeHelper.flipGridVertically;
import static com.games.tictactoe.TicTacToeHelper.flipGridHorizontally;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMajorDiagonal;
import static com.games.tictactoe.TicTacToeHelper.flipGridAlongMinorDiagonal;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.tictactoe.TicTacToeHelper.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TicTacToeHelperTest {

  private List<TokenType> originalGrid;
  private List<TokenType> gridFlippedVertically;
  private List<TokenType> gridFlippedHorizontally;
  private List<TokenType> gridFlippedAlongMajorDiagonal;
  private List<TokenType> gridFlippedAlongMinorDiagonal;

  public TicTacToeHelperTest() {
    /*
     *    | X |
     * -----------
     *  O |   |
     * -----------
     *  X |   |
     */
    originalGrid =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    originalGrid.set(1, TokenType.X);
    originalGrid.set(3, TokenType.O);
    originalGrid.set(6, TokenType.X);

    /*
     *  X |   |
     * -----------
     *  O |   |
     * -----------
     *    | X |
     */
    gridFlippedVertically =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedVertically.set(0, TokenType.X);
    gridFlippedVertically.set(3, TokenType.O);
    gridFlippedVertically.set(7, TokenType.X);

    /*
     *    | X |
     * -----------
     *    |   | O
     * -----------
     *    |   | X
     */
    gridFlippedHorizontally =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedHorizontally.set(1, TokenType.X);
    gridFlippedHorizontally.set(5, TokenType.O);
    gridFlippedHorizontally.set(8, TokenType.X);

    /*
     *    | O | X
     * -----------
     *  X |   |
     * -----------
     *    |   |
     */
    gridFlippedAlongMajorDiagonal =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedAlongMajorDiagonal.set(3, TokenType.X);
    gridFlippedAlongMajorDiagonal.set(1, TokenType.O);
    gridFlippedAlongMajorDiagonal.set(2, TokenType.X);

    /*
     *    |   |
     * -----------
     *    |   | X
     * -----------
     *  X | O |
     */
    gridFlippedAlongMinorDiagonal =
        new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    gridFlippedAlongMinorDiagonal.set(5, TokenType.X);
    gridFlippedAlongMinorDiagonal.set(7, TokenType.O);
    gridFlippedAlongMinorDiagonal.set(6, TokenType.X);
  }

  @Test
  public void testVerticalGridFlip() {
    assertThat(flipGridVertically(originalGrid),
               equalTo(gridFlippedVertically));
  }

  @Test
  public void testHorizontalGridFlip() {
    assertThat(flipGridHorizontally(originalGrid),
               equalTo(gridFlippedHorizontally));
  }

  @Test
  public void testMajorDiagonalGridFlip() {
    assertThat(flipGridAlongMajorDiagonal(originalGrid),
               equalTo(gridFlippedAlongMajorDiagonal));
  }

  @Test
  public void testMinorDiagonalGridFlip() {
    assertThat(flipGridAlongMinorDiagonal(originalGrid),
               equalTo(gridFlippedAlongMinorDiagonal));
  }
}
