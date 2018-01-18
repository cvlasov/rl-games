package tictactoe;

import general.Game;
import general.Agent;

/**  */
public class TicTacToeGame implements Game {

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

  enum Winner {
    X,
    O,
    DRAW,
    GAME_NOT_OVER
  }

  public static final int WIN_RETURN = 1;
  public static final int LOSS_RETURN = -1;
  public static final int DRAW_RETURN = 0;
  public static final int GAME_IN_PROGRESS_RETURN = 0;
  
  private TicTacToeState state;
  private int swapAgentOrder;
  private Agent agent1;  // plays with Token.X
  private Agent agent2;  // plays with Token.O

  public TicTacToeGame(Agent a1, Agent a2) {
    swapAgentOrder = (int) (Math.random()*2); // 0 (don't swap) or 1 (swap)

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
   *
   *  @return winning agent (1 = agent 1, 2 = agent 2)
   */
  @Override
  public int play() {
    //System.out.println("Game: starting the game");
    
    agent1.initializeBeforeNewGame();
    agent2.initializeBeforeNewGame();
    
    boolean firstTurn = true;
    int winner = -1;

    TicTacToeAction agent2Action = null;
    TicTacToeState stateAfterAgent2 = null;
    int agent1Return;

    while (true) {
      // AGENT 1'S TURN
      //System.out.println("***Agent 1's turn***");
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
            agent1.giveReturn(DRAW_RETURN);
            agent2.giveReturn(DRAW_RETURN);
            break;
          case X:
            winner = (swapAgentOrder == 0 ? 1 : 2);
            agent1.giveReturn(WIN_RETURN);
            agent2.giveReturn(LOSS_RETURN);
            break;
          case O:
            winner = (swapAgentOrder == 0 ? 2 : 1);
            agent1.giveReturn(WIN_RETURN);
            agent2.giveReturn(LOSS_RETURN);
            break;
        }
        
        //agent1Return = -agent2Return; // since it's a zero-sum game
        //agent1.giveReturn(agent1Return);
        //winner = gameWinner(stateAfterAgent1.getWinner(), swapAgentOrder);
        agent1.gameOver();
        agent2.gameOver();
        break;
      
      } else {
        if (!firstTurn) {
          //agent2Return =
          //        calculateReturn(state, agent2Action, stateAfterAgent2,
          //                agent1Action, stateAfterAgent1);
          //agent2.giveReturn(agent2Return);
          
          agent2.giveReturn(GAME_IN_PROGRESS_RETURN);
        }
      }

      firstTurn = false;
      state = stateAfterAgent1;

      // AGENT 2'S TURN
      //System.out.println("***Agent 2's turn***");
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
            agent1.giveReturn(DRAW_RETURN);
            agent2.giveReturn(DRAW_RETURN);
            break;
          case X:
            winner = (swapAgentOrder == 0 ? 1 : 2);
            agent1.giveReturn(LOSS_RETURN);
            agent2.giveReturn(WIN_RETURN);
            break;
          case O:
            winner = (swapAgentOrder == 0 ? 2 : 1);
            agent1.giveReturn(LOSS_RETURN);
            agent2.giveReturn(WIN_RETURN);
            break;
        }
        
        //agent2Return = -agent1Return; // since it's a zero-sum game
        //agent2.giveReturn(agent2Return);
        //winner = gameWinner(stateAfterAgent2.getWinner(), swapAgentOrder);
        agent1.gameOver();
        agent2.gameOver();
        break;
      
      } else {
        //agent1Return =
        //        calculateReturn(state, agent1Action, stateAfterAgent1,
        //                agent2Action, stateAfterAgent2);
        //agent1.giveReturn(agent1Return);
        
        agent1.giveReturn(GAME_IN_PROGRESS_RETURN);
      }

      state = stateAfterAgent2;
    }
    
    return winner;
  }

  /** Calculates the return for Agent X, after Agent Y executes an action. */
  public int calculateReturn(TicTacToeState state,
                             TicTacToeAction agentXAction,
                             TicTacToeState stateAfterAgentX,
                             TicTacToeAction agentYAction,
                             TicTacToeState stateAfterAgentY) {
    stateAfterAgentY.checkIfTerminalState();
    int returnAmount = 0;

    switch (stateAfterAgentY.getWinner()) {
      case X: returnAmount = (agentXAction.tokenType == TokenType.O ? -1 : 1);
      case O: returnAmount = (agentXAction.tokenType == TokenType.X ? -1 : 1);
      case DRAW: // fall through
      case GAME_NOT_OVER: returnAmount = 0;
    }

    return returnAmount;
  }

  /**
   *
   * @param winner
   * @param swapAgentOrder 0 means no swap, 1 means swap
   * @return
   */
  private int gameWinner(Winner winner, int swapAgentOrder) {
    int w = -1;

    switch (winner) {
      case GAME_NOT_OVER: w = -1; break;
      case DRAW:          w = 0; break;
      case X: w = (swapAgentOrder == 0 ? 1 : 2); break;
      case O: w = (swapAgentOrder == 0 ? 2 : 1); break;
    }

    return w;
  }
}
