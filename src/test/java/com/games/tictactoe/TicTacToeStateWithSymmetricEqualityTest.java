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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
    List<TokenType> originalGrid = Arrays.asList(
        new TokenType[] {
          TokenType.NONE, TokenType.X,    TokenType.NONE,
          TokenType.O,    TokenType.NONE, TokenType.NONE,
          TokenType.X,    TokenType.NONE, TokenType.NONE
        });

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
    assertThat(originalState,
               equalTo(stateFlippedAlongMajorDiagonalAndHorizontally));
  }

  @Test
  public void testMinorDiagonalSymmetryEquality() {
    assertThat(originalState, equalTo(stateFlippedAlongMinorDiagonal));
  }

  @Test
  public void testMinorDiagonalAndHorizontalSymmetryEquality() {
    assertThat(originalState,
               equalTo(stateFlippedAlongMinorDiagonalAndHorizontally));
  }

  @Test
  public void testLackOfSymmetryInequality() {
    /*
     * Testing inequality between:
     *    | X |             |   |
     * -----------       -----------
     *  O |   |     and   O | x |
     * -----------       -----------
     *  X |   |           X |   |
     */
    TicTacToeStateWithSymmetricEquality nonSymmetricalState =
        new TicTacToeStateWithSymmetricEquality(Arrays.asList(
            new TokenType[] {
              TokenType.NONE, TokenType.NONE, TokenType.NONE,
              TokenType.O,    TokenType.X,    TokenType.NONE,
              TokenType.X,    TokenType.NONE, TokenType.NONE
            }));

    assertThat(originalState, not(equalTo(nonSymmetricalState)));
  }

  @Test
  public void testVerticalSymmetryGivesSameActions() {
    assertThat(
        new HashSet<>(originalState.getActions()),
        equalTo(new HashSet<> (stateFlippedVertically.getActions())));
  }

  @Test
  public void testHorizontalSymmetryGivesSameActions() {
    assertThat(
        new HashSet<>(originalState.getActions()),
        equalTo(new HashSet<>(stateFlippedHorizontally.getActions())));
  }

  @Test
  public void testVerticalAndHorizontalSymmetryGivesSameActions() {
    assertThat(
        new HashSet<>(originalState.getActions()),
        equalTo(new HashSet<>(stateFlippedVerticallyAndHorizontally.getActions())));
  }

  @Test
  public void testMajorDiagonalSymmetryGivesSameActions() {
    assertThat(
        new HashSet<>(originalState.getActions()),
        equalTo(new HashSet<>(stateFlippedAlongMajorDiagonal.getActions())));
  }

  @Test
  public void testMajorDiagonalAndHorizontalSymmetryGivesSameActions() {
    assertThat(
        new HashSet<>(originalState.getActions()),
        equalTo(new HashSet<>(stateFlippedAlongMajorDiagonalAndHorizontally.getActions())));
  }

  @Test
  public void testMinorDiagonalSymmetryGivesSameActions() {
    assertThat(
        new HashSet<>(originalState.getActions()),
        equalTo(new HashSet<>(stateFlippedAlongMinorDiagonal.getActions())));
  }

  @Test
  public void testMinorDiagonalAndHorizontalSymmetryGivesSameActions() {
    assertThat(
        new HashSet<>(originalState.getActions()),
        equalTo(new HashSet<>(stateFlippedAlongMinorDiagonalAndHorizontally.getActions())));
  }

  @Test
  public void testLackOfSymmetryGivesDifferentActions() {
    /*
     * Testing inequality between:
     *    | X |             |   |
     * -----------       -----------
     *  O |   |     and   O | x |
     * -----------       -----------
     *  X |   |           X |   |
     */
    TicTacToeStateWithSymmetricEquality nonSymmetricalState =
        new TicTacToeStateWithSymmetricEquality(Arrays.asList(
            new TokenType[] {
              TokenType.NONE, TokenType.NONE, TokenType.NONE,
              TokenType.O,    TokenType.X,    TokenType.NONE,
              TokenType.X,    TokenType.NONE, TokenType.NONE
            }));

    assertThat(
        new HashSet<>(originalState.getActions()),
        not(equalTo(new HashSet<>(nonSymmetricalState.getActions()))));
  }
}
