package com.games.tictactoe;

import static com.games.tictactoe.TicTacToeHelper.DRAW_RETURN;
import static com.games.tictactoe.TicTacToeHelper.GAME_IN_PROGRESS_RETURN;
import static com.games.tictactoe.TicTacToeHelper.LOSS_RETURN;
import static com.games.tictactoe.TicTacToeHelper.WIN_RETURN;

import com.games.general.Action;
import com.games.general.Agent;
import com.games.general.Game;
import com.games.tictactoe.TicTacToeHelper.TokenType;
import com.games.tictactoe.TicTacToeHelper.Winner;
import com.google.common.annotations.VisibleForTesting;

/**
 * Game of Tic-Tac-Toe where the the first player is chosen randomly. The first
 * player always uses X token and the second player uses O tokens.
 */
public abstract class TicTacToeGame implements Game {

  /** Current state of the game. */
  protected TicTacToeState state;

  /** 0 (don't swap order of agents passed to the contructor) or 1 (swap). */
  protected int swapAgentOrder;

  /** Agent that goes first and plays with {@link #TokenType.X}. */
  protected Agent agent1;

  /** Agent that goes second and plays with {@link #TokenType.O}. */
  protected Agent agent2;

  protected TicTacToeGame(Agent a1, Agent a2) {
    swapAgentOrder = (int) (Math.random() * 2);

    if (swapAgentOrder == 0) {
      this.agent1 = a1;
      this.agent2 = a2;

    } else {
      this.agent1 = a2;
      this.agent2 = a1;
    }

    // Subclasses must instantiate "state" to the appropriate subclass of
    // TicTacToeState
  }

  protected TicTacToeGame(Agent a1, Agent a2, int swapAgentOrder) {
    this.swapAgentOrder = swapAgentOrder;

    if (swapAgentOrder == 0) {
      this.agent1 = a1;
      this.agent2 = a2;

    } else {
      this.agent1 = a2;
      this.agent2 = a1;
    }

    // Subclasses must instantiate "state" to the appropriate subclass of
    // TicTacToeState
  }

  /**
   * Plays one game and returns the winning agent.
   *
   * @return winning agent (-1 = not over, 0 = draw, 1 = agent 1, 2 = agent 2)
   */
  @Override
  public int play() {
    agent1.initializeBeforeNewGame();
    agent2.initializeBeforeNewGame();

    boolean firstTurn = true;
    int winner = -1;

    Action agent1Action = null;
    TicTacToeState stateAfterAgent1 = null;
    Action agent2Action = null;
    TicTacToeState stateAfterAgent2 = state;  // initial state

    while (true) {
      // AGENT 1'S TURN
      agent1Action = agent1.chooseAction(stateAfterAgent2);
      stateAfterAgent1 = (TicTacToeState) stateAfterAgent2.applyAction(agent1Action);

      // If Agent 1 just made the game end
      if (stateAfterAgent1.isTerminalState()) {
        winner = gameOver(stateAfterAgent1);
        break;

      } else {
        if (!firstTurn) {
          agent2.receiveReturn(GAME_IN_PROGRESS_RETURN);
        }
      }

      firstTurn = false;

      // AGENT 2'S TURN
      agent2Action = agent2.chooseAction(stateAfterAgent1);
      stateAfterAgent2 = (TicTacToeState) stateAfterAgent1.applyAction(agent2Action);

      // If Agent 2 just made the game end
      if (stateAfterAgent2.isTerminalState()) {
        winner = gameOver(stateAfterAgent2);
        break;

      } else {
        agent1.receiveReturn(GAME_IN_PROGRESS_RETURN);
      }
    }

    return winner;
  }

 /**
  * Determines which agent (if any) is the winner at the given state.
  *
  * @return winning agent (-1 = not over, 0 = draw, 1 = agent 1, 2 = agent 2)
  */
  @VisibleForTesting
  int gameOver(TicTacToeState terminalState) {
    int winner = Integer.MIN_VALUE;

    switch (terminalState.getWinner()) {
      case GAME_NOT_OVER:
        winner = -1;
        break;
      case DRAW:
        winner = 0;
        agent1.receiveReturn(DRAW_RETURN);
        agent2.receiveReturn(DRAW_RETURN);
        break;
      case X:
        winner = (swapAgentOrder == 0 ? 1 : 2);
        agent1.receiveReturn(WIN_RETURN);
        agent2.receiveReturn(LOSS_RETURN);
        break;
      case O:
        winner = (swapAgentOrder == 0 ? 2 : 1);
        agent1.receiveReturn(LOSS_RETURN);
        agent2.receiveReturn(WIN_RETURN);
        break;
    }

    agent1.gameOver();
    agent2.gameOver();
    return winner;
  }
}
