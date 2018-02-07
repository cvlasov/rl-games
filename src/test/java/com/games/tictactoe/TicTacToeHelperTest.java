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

import org.junit.Ignore;
import org.junit.Test;

public class TicTacToeHelperTest {

  public TicTacToeHelperTest() {}

  @Test
  public void testVerticalGridFlip() {
    /*
     * Testing equality between:
     *    | X |           X |   |
     * -----------       -----------
     *  O |   |     and   O |   |
     * -----------       -----------
     *  X |   |             | X |
     *  (original)        (expected)
     */

    List<TokenType> grid1 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid1.set(1, TokenType.X);
    grid1.set(3, TokenType.O);
    grid1.set(6, TokenType.X);

    List<TokenType> grid2 = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    grid2.set(0, TokenType.X);
    grid2.set(3, TokenType.O);
    grid2.set(7, TokenType.X);

    assertThat(flipGridVertically(grid1), equalTo(grid2));
  }

  @Test
  public void testHorizontalGridFlip() {
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

    assertThat(flipGridHorizontally(grid1), equalTo(grid2));
  }

  @Test
  public void testVerticalAndHorizontalGridFlip() {
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

    assertThat(flipGridVertically(flipGridHorizontally(grid1)), equalTo(grid2));
  }

  @Test
  public void testMajorDiagonalGridFlip() {
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

    assertThat(flipGridAlongMajorDiagonal(grid1), equalTo(grid2));
  }

  @Test
  public void testMinorDiagonalGridFlip() {
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

    assertThat(flipGridAlongMinorDiagonal(grid1), equalTo(grid2));
  }
}
