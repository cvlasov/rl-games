package com.games.chungtoi;

import static com.games.chungtoi.ChungToiHelper.DRAW_RETURN;
import static com.games.chungtoi.ChungToiHelper.GRID_SIZE;
import static com.games.chungtoi.ChungToiHelper.LOSS_RETURN;
import static com.games.chungtoi.ChungToiHelper.WIN_RETURN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.games.chungtoi.ChungToiGame;
import com.games.chungtoi.ChungToiPassAction;
import com.games.chungtoi.ChungToiPutAction;
import com.games.chungtoi.ChungToiHelper.Player;
import com.games.chungtoi.ChungToiHelper.TokenType;
import com.games.general.Action;
import com.games.general.Agent;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(Parameterized.class)
public class ChungToiGameTest {

  private Agent mockAgent1 = mock(Agent.class);
  private Agent mockAgent2 = mock(Agent.class);

  private static ChungToiState winningXState =
      new ChungToiState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE,       TokenType.NONE,       TokenType.X_NORMAL,
          TokenType.O_NORMAL,   TokenType.X_DIAGONAL, TokenType.O_NORMAL,
          TokenType.X_DIAGONAL, TokenType.O_DIAGONAL, TokenType.NONE
        }),
        Player.O);

  private static ChungToiState winningOState =
      new ChungToiState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE,       TokenType.NONE,       TokenType.O_NORMAL,
          TokenType.X_NORMAL,   TokenType.O_DIAGONAL, TokenType.X_NORMAL,
          TokenType.O_DIAGONAL, TokenType.X_DIAGONAL, TokenType.NONE
        }),
        Player.X);

  private static ChungToiState gameNotOverState =
      new ChungToiState(
        Arrays.asList(new TokenType[] {
          TokenType.NONE,       TokenType.NONE,       TokenType.O_NORMAL,
          TokenType.X_NORMAL,   TokenType.NONE,       TokenType.X_NORMAL,
          TokenType.O_DIAGONAL, TokenType.X_DIAGONAL, TokenType.NONE
        }),
        Player.X);

  @Parameter(0)
  public int swapAgents;  // 0 = no swap, 1 = swap

  @Parameter(1)
  public ChungToiState state;

  @Parameter(2)
  public int expectedWinner;  // 0 = draw, 1 = agent 1, 2 = agent 2

  @Parameter(3)
  public int expectedAgent1Return;

  @Parameter(4)
  public int expectedAgent2Return;

  @Parameters
  public static Collection<Object[]> agentConfigurations() {
     return Arrays.asList(new Object[][] {
        { 0, winningXState,    1, WIN_RETURN,  LOSS_RETURN },
        { 0, winningOState,    2, LOSS_RETURN, WIN_RETURN },
        { 1, winningOState,    1, WIN_RETURN,  LOSS_RETURN },
        { 1, winningXState,    2, LOSS_RETURN, WIN_RETURN },
        { 0, gameNotOverState, 0, DRAW_RETURN, DRAW_RETURN },
        { 1, gameNotOverState, 0, DRAW_RETURN, DRAW_RETURN }
     });
  }

  @Test
  public void testAgentsNotSwappedInConstructor() {
    ChungToiGame game =
        new ChungToiGame(mockAgent1, mockAgent2, 0 /* no swap */);
    assertThat(game.agent1, equalTo(mockAgent1));
    assertThat(game.agent2, equalTo(mockAgent2));
  }

  @Test
  public void testAgentsSwappedInConstructor() {
    ChungToiGame game =
        new ChungToiGame(mockAgent1, mockAgent2, 1 /* swap */);
    assertThat(game.agent1, equalTo(mockAgent2));
    assertThat(game.agent2, equalTo(mockAgent1));
  }

  @Test
  public void testCorrectWinner() {
    ChungToiGame game =
        new ChungToiGame(mockAgent1, mockAgent2, swapAgents);
    int winner = game.gameOver(state);
    assertThat(winner, equalTo(expectedWinner));
  }

  @Test
  public void testCorrectReturns() {
    ChungToiGame game =
        new ChungToiGame(mockAgent1, mockAgent2, swapAgents);
    game.gameOver(state);
    verify(mockAgent1).receiveReturn(expectedAgent1Return);
    verify(mockAgent2).receiveReturn(expectedAgent2Return);
  }

  @Test
  public void testBothAgentsPassedIsTrue() {
    ChungToiPassAction action1 = ChungToiPassAction.getInstance();
    ChungToiPassAction action2 = ChungToiPassAction.getInstance();
    ChungToiGame game = new ChungToiGame(mock(Agent.class), mock(Agent.class));
    assertTrue(game.bothAgentsPassed(action1,action2));
  }

  @Test
  public void testBothAgentsPassedIsFalse() {
    Action action1 = ChungToiPassAction.getInstance();
    Action action2 = new ChungToiPutAction(TokenType.X_NORMAL, 0);
    ChungToiGame game = new ChungToiGame(mock(Agent.class), mock(Agent.class));
    assertFalse(game.bothAgentsPassed(action1,action2));
  }
}
