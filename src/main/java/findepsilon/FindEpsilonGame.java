package findepsilon;

import agents.MonteCarloAgent;
import agents.RandomAgent;
import general.Game;
import general.Agent;
import tictactoe.TicTacToeGame;
import tictactoe.TicTacToeResults;

/**
 * Game of Find Epsilon, where there is one player and the game ends after one
 * move. The available actions are all possible values of epsilon and each
 * return is the win rate for using the chosen value of epsilon.
 */
public class FindEpsilonGame implements Game {
  
  /** Number of games of Tic-Tac-Toe to play with the chosen epsilon value. */
  private static final int numTicTacToeGames = 10000;
  
  /** Granularity of possible choices of epsilon. */
  public static final double granularityOfEpsilon = 0.01;
  
  /** Current state of the game. */
  private FindEpsilonState state;
  
  /** The sole player of the game. */
  private Agent agent;

  public FindEpsilonGame(Agent a) {
    this.agent = a;
    this.state = FindEpsilonState.getInstance();
  }

  /**
   * Plays one game and returns zero.
   *
   * @return zero
   */
  @Override
  public int play() {
    agent.initializeBeforeNewGame();
    
    FindEpsilonAction action =
            (FindEpsilonAction) agent.chooseAction(state);
    state = (FindEpsilonState) state.applyAction(action);

    TicTacToeResults results =
        playTicTacToe(action.epsilon /* choice of epsilon */,
                      numTicTacToeGames);
    agent.receiveReturn(results.wins /
                        (results.wins + results.losses + results.draws));
    
    return 0;
  }
  
  /**
   * Plays the given number of games of Tic-Tac-Toe between a Monte Carlo agent
   * (with the given value of epsilon) and a random agent.
   *
   * @param epsilon  the value of epsilon to use for the Monte Carlo agent
   * @param numGames the number of games of Tic-Tac-Toe to play
   * @return the results of playing all the games
   */
  protected TicTacToeResults playTicTacToe(double epsilon, int numGames) {
    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGame game;
    
    MonteCarloAgent monteCarlo = new MonteCarloAgent(epsilon);
    RandomAgent random = new RandomAgent();
    
    for (int i = 0 ; i < numGames ; i++) {
      game = new TicTacToeGame(monteCarlo, random);
      int winner = game.play();

      if (winner == -1) {
        System.out.println("ERROR");
      }

      wins[winner]++;
    }
    
    System.out.println("epsilon = " + epsilon);
    
    return new TicTacToeResults(wins[1] /* wins   */,
                                wins[2] /* losses */,
                                wins[0] /* draws  s*/);
  }
}
