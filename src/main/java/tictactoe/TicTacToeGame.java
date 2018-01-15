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

  private TicTacToeState state;

  private Agent agent1;  // plays with Token.X
  private Agent agent2;  // plays with Token.O

  public TicTacToeGame(Agent a1, Agent a2) {
    this.agent1 = a1;
    this.agent2 = a2;
    state = new TicTacToeState();
  }

  @Override
  public void initialize() {
    state = new TicTacToeState();
    //state.print();
  }

  /**
   *
   *  @return winning agent (1 = agent 1, 2 = agent 2)
   */
  @Override
  public int playOneGame() {
    //System.out.println("Starting a new game");
    int swapAgentOrder = (int) (Math.random()*2); // 0 (don't swap) or 1 (swap)

    if (swapAgentOrder == 1) {
      Agent temp = agent1;
      agent1 = agent2;
      agent2 = agent1;
    }

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
      //state.print();

      int agent2Return = Integer.MIN_VALUE;

      if (!firstTurn) {
        agent2Return =
                calculateReturn(state, agent2Action, stateAfterAgent2,
                        agent1Action, stateAfterAgent1);
        agent2.giveReturn(agent2Return);
      }

      // If Agent 1 just made the game end
      if (stateAfterAgent1.checkIfTerminalState()) {
        agent1Return = -agent2Return; // since it's a zero-sum game
        agent1.giveReturn(agent1Return);
        winner = gameWinner(stateAfterAgent1.getWinner(), swapAgentOrder);
        agent1.gameOver();
        agent2.gameOver();
        break;
      }

      firstTurn = false;
      state = stateAfterAgent1;

      // AGENT 2'S TURN
      //System.out.println("***Agent 2's turn***");
      agent2Action = (TicTacToeAction) agent2.chooseAction(state);
      stateAfterAgent2 = (TicTacToeState) state.applyAction(agent2Action);
      //state.print();

      agent1Return =
              calculateReturn(state, agent1Action, stateAfterAgent1,
                      agent2Action, stateAfterAgent2);
      agent1.giveReturn(agent1Return);

      // If Agent 2 just made the game end
      if (stateAfterAgent2.checkIfTerminalState()) {
        agent2Return = -agent1Return; // since it's a zero-sum game
        agent2.giveReturn(agent2Return);
        winner = gameWinner(stateAfterAgent2.getWinner(), swapAgentOrder);
        agent1.gameOver();
        agent2.gameOver();
        break;
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
      case DRAW: // cascade
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
