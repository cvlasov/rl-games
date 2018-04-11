package com.games.nim;

import static com.games.nim.NimHelper.LOSS_RETURN;
import static com.games.nim.NimHelper.NUM_PILES;
import static com.games.nim.NimHelper.WIN_RETURN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.games.nim.NimHelper.Player;
import com.games.general.Agent;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(Parameterized.class)
public class NimGameTest {

  private Agent mockAgent1 = mock(Agent.class);
  private Agent mockAgent2 = mock(Agent.class);

  private static NimState winningXState =
      new NimState(Player.X, new int[NUM_PILES]);

  private static NimState winningOState =
      new NimState(Player.O, new int[NUM_PILES]);

  @Parameter(0)
  public int swapAgents;  // 0 = no swap, 1 = swap

  @Parameter(1)
  public NimState state;

  @Parameter(2)
  public int expectedWinner;  // 1 = agent 1, 2 = agent 2

  @Parameter(3)
  public int expectedAgent1Return;

  @Parameter(4)
  public int expectedAgent2Return;

  @Parameters
  public static Collection<Object[]> agentConfigurations() {
     return Arrays.asList(new Object[][] {
        { 0, winningXState, 1, WIN_RETURN,  LOSS_RETURN },
        { 1, winningXState, 2, LOSS_RETURN, WIN_RETURN },
        { 0, winningOState, 2, LOSS_RETURN, WIN_RETURN },
        { 1, winningOState, 1, WIN_RETURN,  LOSS_RETURN }
     });
  }

  @Test
  public void testAgentsNotSwappedInConstructor() {
    NimGame game = new NimGame(mockAgent1, mockAgent2, 0 /* no swap */);
    assertThat(game.agent1, equalTo(mockAgent1));
    assertThat(game.agent2, equalTo(mockAgent2));
  }

  @Test
  public void testAgentsSwappedInConstructor() {
    NimGame game = new NimGame(mockAgent1, mockAgent2, 1 /* swap */);
    assertThat(game.agent1, equalTo(mockAgent2));
    assertThat(game.agent2, equalTo(mockAgent1));
  }

  @Test
  public void testCorrectWinner() {
    NimGame game = new NimGame(mockAgent1, mockAgent2, swapAgents);
    int winner = game.gameOver(state);
    assertThat(winner, equalTo(expectedWinner));
  }

  @Test
  public void testCorrectReturns() {
    NimGame game = new NimGame(mockAgent1, mockAgent2, swapAgents);
    game.gameOver(state);
    verify(mockAgent1).receiveReturn(expectedAgent1Return);
    verify(mockAgent2).receiveReturn(expectedAgent2Return);
  }
}
