package tictactoe;

import agents.MonteCarloAgent;
import agents.RandomAgent;
import general.Agent;

import java.util.HashSet;

/** Main class for making agents play Tic-Tac-Toe against east other. */
public class Main {

  public static void main(String[] args) {
    MonteCarloAgent mc1 = new MonteCarloAgent(0.05 /* epsilon */);
    RandomAgent r1 = new RandomAgent();
    runExperiment(mc1, r1, 50000 /* number of games */);

    RandomAgent r2 = new RandomAgent();
    RandomAgent r3 = new RandomAgent();
    runExperiment(r2, r3, 50000 /* number of games */);
  }
  
  /**
   * Plays two agents against each other and prints the results to the console.
   *
   * @param agent1   the first player
   * @param agent2   the second player
   * @param numGames the number of games they will play against each other
   */
  private static void runExperiment(Agent agent1, Agent agent2, int numGames) {
    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGame game;
    
    for (int i = 0 ; i < numGames ; i++) {
      game = new TicTacToeGame(agent1, agent2);
      int winner = game.play();

      if (winner == -1) {
        System.out.println("ERROR");
      }

      wins[winner]++;
    }

    System.out.println("Draw   : " + wins[0]);
    System.out.println(agent1.getName() + " : " + wins[1]);
    System.out.println(agent2.getName() + " : " + wins[2]);
    System.out.println();
  }
}
