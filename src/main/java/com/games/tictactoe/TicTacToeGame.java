package com.games.tictactoe;

import com.games.general.Game;
import com.games.general.Agent;

/**
 * Game of Tic-Tac-Toe where the the first player is chosen randomly. The first
 * player always uses X token and the second player uses O tokens.
 */
public class TicTacToeGame implements Game {

  /** Contents of a cell on the board. */
  enum TokenType {
    X ("X"),
    O ("O"),
    NONE (" ");

    private final String name;

    TokenType(String s) {
      name = s;
    }

    public String toString() {
      return this.name;
    }
  }

  /** Winner of the game, if any. */
  enum Winner {
    X,
    O,
    DRAW,
    GAME_NOT_OVER
  }

  /** Return for an action that causes the game to end and results in a win. */
  public static final int WIN_RETURN = 1;
  
  /** Return for an action that causes the game to end and results in a loss. */
  public static final int LOSS_RETURN = -1;
  
  /** Return for an action that causes the game to end and results in a draw. */
  public static final int DRAW_RETURN = 0;
  
  /** Return for an action that does not cause the game to end. */
  public static final int GAME_IN_PROGRESS_RETURN = 0;
  
  /** Current state of the game. */
  private TicTacToeState state;
  
  /** 0 (don't swap order of agents passed to the contructor) or 1 (swap). */
  private int swapAgentOrder;
  
  /** Agent that goes first and plays with {@link #TokenType.X}. */
  private Agent agent1;
  
  /** Agent that goes second and plays with {@link #TokenType.O}. */
  private Agent agent2;

  public TicTacToeGame(Agent a1, Agent a2) {
    swapAgentOrder = (int) (Math.random() * 2);

    if (swapAgentOrder == 0) {
      this.agent1 = a1;
      this.agent2 = a2;
      
    } else {
      this.agent1 = a2;
      this.agent2 = a1;
    }
    
    state = new TicTacToeState();
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

    TicTacToeAction agent2Action = null;
    TicTacToeState stateAfterAgent2 = null;
    int agent1Return;

    while (true) {
      // AGENT 1'S TURN
      TicTacToeAction agent1Action =
              (TicTacToeAction) agent1.chooseAction(state);
      TicTacToeState stateAfterAgent1 =
              (TicTacToeState) state.applyAction(agent1Action);

      int agent2Return = Integer.MIN_VALUE;

      // If Agent 1 just made the game end
      if (stateAfterAgent1.checkIfTerminalState()) {
        switch (stateAfterAgent1.getWinner()) {
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
            agent1.receiveReturn(WIN_RETURN);
            agent2.receiveReturn(LOSS_RETURN);
            break;
        }
        
        agent1.gameOver();
        agent2.gameOver();
        break;
      
      } else {
        if (!firstTurn) {
          agent2.receiveReturn(GAME_IN_PROGRESS_RETURN);
        }
      }

      firstTurn = false;
      state = stateAfterAgent1;

      // AGENT 2'S TURN
      agent2Action = (TicTacToeAction) agent2.chooseAction(state);
      stateAfterAgent2 = (TicTacToeState) state.applyAction(agent2Action);

      // If Agent 2 just made the game end
      if (stateAfterAgent2.checkIfTerminalState()) {
        switch (stateAfterAgent2.getWinner()) {
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
            agent1.receiveReturn(LOSS_RETURN);
            agent2.receiveReturn(WIN_RETURN);
            break;
          case O:
            winner = (swapAgentOrder == 0 ? 2 : 1);
            agent1.receiveReturn(LOSS_RETURN);
            agent2.receiveReturn(WIN_RETURN);
            break;
        }
        
        agent1.gameOver();
        agent2.gameOver();
        break;
      
      } else {
        agent1.receiveReturn(GAME_IN_PROGRESS_RETURN);
      }

      state = stateAfterAgent2;
    }
    
    return winner;
  }
}
