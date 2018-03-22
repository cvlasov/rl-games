package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.DRAW_RETURN;
import static com.games.tictactoe.TicTacToeHelper.GRID_SIZE;
import static com.games.tictactoe.TicTacToeHelper.LOSS_RETURN;
import static com.games.tictactoe.TicTacToeHelper.WIN_RETURN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.general.Agent;
import com.games.tictactoe.TicTacToeHelper.Player;
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.games.tictactoe.TicTacToeNormalGame;
import com.games.tictactoe.TicTacToeNormalState;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(Parameterized.class)
public class TicTacToeNormalGameTest {

  private Agent mockAgent1 = mock(Agent.class);
  private Agent mockAgent2 = mock(Agent.class);

  private static TicTacToeNormalState winningXState =
      new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.O, TokenType.X, TokenType.X,
          TokenType.O, TokenType.X, TokenType.O,
          TokenType.X, TokenType.O, TokenType.X
        }),
        Player.O);

  private static TicTacToeNormalState winningOState =
      new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.O, TokenType.O, TokenType.O,
          TokenType.X, TokenType.O, TokenType.X,
          TokenType.X, TokenType.X, TokenType.NONE
        }),
        Player.X);

  private static TicTacToeNormalState drawState =
      new TicTacToeNormalState(
        Arrays.asList(new TokenType[] {
          TokenType.X, TokenType.O, TokenType.X,
          TokenType.O, TokenType.O, TokenType.X,
          TokenType.X, TokenType.X, TokenType.O
        }),
        Player.O);

  @Parameter(0)
  public int swapAgents;  // 0 = no swap, 1 = swap

  @Parameter(1)
  public TicTacToeNormalState state;

  @Parameter(2)
  public int expectedWinner;  // 0 = draw, 1 = agent 1, 2 = agent 2

  @Parameter(3)
  public int expectedAgent1Return;

  @Parameter(4)
  public int expectedAgent2Return;

  @Parameters
  public static Collection<Object[]> agentConfigurations() {
     return Arrays.asList(new Object[][] {
        { 0, winningXState, 1, WIN_RETURN,  LOSS_RETURN },
        { 0, winningOState, 2, LOSS_RETURN, WIN_RETURN },
        { 1, winningOState, 1, WIN_RETURN,  LOSS_RETURN },
        { 1, winningXState, 2, LOSS_RETURN, WIN_RETURN },
        { 0, drawState,     0, DRAW_RETURN, DRAW_RETURN },
        { 1, drawState,     0, DRAW_RETURN, DRAW_RETURN }
     });
  }

  @Test
  public void testAgentsNotSwappedInConstructor() {
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, 0 /* no swap */);
    assertThat(game.agent1, equalTo(mockAgent1));
    assertThat(game.agent2, equalTo(mockAgent2));
  }

  @Test
  public void testAgentsSwappedInConstructor() {
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, 1 /* swap */);
    assertThat(game.agent1, equalTo(mockAgent2));
    assertThat(game.agent2, equalTo(mockAgent1));
  }

  @Test
  public void testCorrectWinner() {
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, swapAgents);
    int winner = game.gameOver(state);
    assertThat(winner, equalTo(expectedWinner));
  }

  @Test
  public void testCorrectReturns() {
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, swapAgents);
    game.gameOver(state);
    verify(mockAgent1).receiveReturn(expectedAgent1Return);
    verify(mockAgent2).receiveReturn(expectedAgent2Return);
  }
}
