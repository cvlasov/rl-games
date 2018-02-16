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
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.games.tictactoe.TicTacToeNormalGame;
import com.games.tictactoe.TicTacToeNormalState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TicTacToeNormalGameTest {

  private Agent mockAgent1 = mock(Agent.class);
  private Agent mockAgent2 = mock(Agent.class);

  private List<TokenType> emptyGrid;
  private List<TokenType> winningXGrid;
  private List<TokenType> winningOGrid;
  private List<TokenType> drawGrid;

  private TicTacToeNormalState emptyState;
  private TicTacToeNormalState winningXState;
  private TicTacToeNormalState winningOState;
  private TicTacToeNormalState drawState;

  public TicTacToeNormalGameTest() {
    emptyGrid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));

    winningXGrid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    winningXGrid.set(0, TokenType.O);
    winningXGrid.set(1, TokenType.X);
    winningXGrid.set(2, TokenType.X);
    winningXGrid.set(3, TokenType.O);
    winningXGrid.set(4, TokenType.X);
    winningXGrid.set(5, TokenType.O);
    winningXGrid.set(6, TokenType.X);
    winningXGrid.set(7, TokenType.O);
    winningXGrid.set(8, TokenType.X);

    winningOGrid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    winningOGrid.set(0, TokenType.O);
    winningOGrid.set(1, TokenType.O);
    winningOGrid.set(2, TokenType.O);
    winningOGrid.set(3, TokenType.X);
    winningOGrid.set(4, TokenType.O);
    winningOGrid.set(5, TokenType.X);
    winningOGrid.set(6, TokenType.X);
    winningOGrid.set(7, TokenType.X);

    drawGrid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    drawGrid.set(0, TokenType.X);
    drawGrid.set(1, TokenType.O);
    drawGrid.set(2, TokenType.X);
    drawGrid.set(3, TokenType.O);
    drawGrid.set(4, TokenType.O);
    drawGrid.set(5, TokenType.X);
    drawGrid.set(6, TokenType.X);
    drawGrid.set(7, TokenType.X);
    drawGrid.set(8, TokenType.O);

    emptyState = new TicTacToeNormalState(emptyGrid);
    winningXState = new TicTacToeNormalState(winningXGrid);
    winningOState = new TicTacToeNormalState(winningOGrid);
    drawState = new TicTacToeNormalState(drawGrid);

    emptyState.checkIfTerminalState();
    winningXState.checkIfTerminalState();
    winningOState.checkIfTerminalState();
    drawState.checkIfTerminalState();
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
  public void testFirstAgentWinsWithoutSwap() {
    // Don't swap agents - mockAgent1 plays with X and mockAgent2 plays with O
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, 0 /* no swap */);
    int winner = game.gameOver(winningXState);

    assertThat(winner, equalTo(1));
    verify(mockAgent1).receiveReturn(WIN_RETURN);
    verify(mockAgent2).receiveReturn(LOSS_RETURN);
  }

  @Test
  public void testSecondAgentWinsWithoutSwap() {
    // Don't swap agents - mockAgent1 plays with X and mockAgent2 plays with O
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, 0 /* no swap */);
    int winner = game.gameOver(winningOState);

    assertThat(winner, equalTo(2));
    verify(mockAgent1).receiveReturn(LOSS_RETURN);
    verify(mockAgent2).receiveReturn(WIN_RETURN);
  }

  @Test
  public void testFirstAgentWinsWithSwap() {
    // Swap agents - mockAgent2 plays with X and mockAgent2 plays with O
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, 1 /* swap */);
    int winner = game.gameOver(winningOState);

    assertThat(winner, equalTo(1));
    verify(mockAgent1).receiveReturn(WIN_RETURN);
    verify(mockAgent2).receiveReturn(LOSS_RETURN);
  }

  @Test
  public void testSecondAgentWinsWithSwap() {
    // Swap agents - mockAgent2 plays with X and mockAgent2 plays with O
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, 1 /* swap */);
    int winner = game.gameOver(winningXState);

    assertThat(winner, equalTo(2));
    verify(mockAgent1).receiveReturn(LOSS_RETURN);
    verify(mockAgent2).receiveReturn(WIN_RETURN);
  }

  @Test
  public void testDrawWithNoSwap() {
    // Don't swap agents - mockAgent1 plays with X and mockAgent2 plays with O
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, 0 /* no swap */);
    int winner = game.gameOver(drawState);

    assertThat(winner, equalTo(0));
    verify(mockAgent1).receiveReturn(DRAW_RETURN);
    verify(mockAgent2).receiveReturn(DRAW_RETURN);
  }

  @Test
  public void testDrawWithSwap() {
    // Swap agents - mockAgent2 plays with X and mockAgent2 plays with O
    TicTacToeNormalGame game =
        new TicTacToeNormalGame(mockAgent1, mockAgent2, 1 /* swap */);
    int winner = game.gameOver(drawState);

    assertThat(winner, equalTo(0));
    verify(mockAgent1).receiveReturn(DRAW_RETURN);
    verify(mockAgent2).receiveReturn(DRAW_RETURN);
  }
}
