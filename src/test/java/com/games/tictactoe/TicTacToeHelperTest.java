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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TicTacToeHelperTest {

  /*
   *    | X |
   * -----------
   *  O |   |
   * -----------
   *  X |   |
   */
  private List<TokenType> originalGrid =
      Arrays.asList(new TokenType[] {
        TokenType.NONE, TokenType.X,    TokenType.NONE,
        TokenType.O,    TokenType.NONE, TokenType.NONE,
        TokenType.X,    TokenType.NONE, TokenType.NONE
      });

  /*
   *  X |   |
   * -----------
   *  O |   |
   * -----------
   *    | X |
   */
  private List<TokenType> gridFlippedVertically =
      Arrays.asList(new TokenType[] {
        TokenType.X,    TokenType.NONE, TokenType.NONE,
        TokenType.O,    TokenType.NONE, TokenType.NONE,
        TokenType.NONE, TokenType.X,    TokenType.NONE
      });

  /*
   *    | X |
   * -----------
   *    |   | O
   * -----------
   *    |   | X
   */
  private List<TokenType> gridFlippedHorizontally =
      Arrays.asList(new TokenType[] {
        TokenType.NONE, TokenType.X,    TokenType.NONE,
        TokenType.NONE, TokenType.NONE, TokenType.O,
        TokenType.NONE, TokenType.NONE, TokenType.X
      });

  /*
   *    | O | X
   * -----------
   *  X |   |
   * -----------
   *    |   |
   */
  private List<TokenType> gridFlippedAlongMajorDiagonal =
      Arrays.asList(new TokenType[] {
        TokenType.NONE, TokenType.O,    TokenType.X,
        TokenType.X,    TokenType.NONE, TokenType.NONE,
        TokenType.NONE, TokenType.NONE, TokenType.NONE
      });

  /*
   *    |   |
   * -----------
   *    |   | X
   * -----------
   *  X | O |
   */
  private List<TokenType> gridFlippedAlongMinorDiagonal =
      Arrays.asList(new TokenType[] {
        TokenType.NONE, TokenType.NONE, TokenType.NONE,
        TokenType.NONE, TokenType.NONE, TokenType.X,
        TokenType.X,    TokenType.O,    TokenType.NONE
      });

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
