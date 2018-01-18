package tictactoe;

import agents.MonteCarloAgent;
import agents.RandomAgent;
import general.Agent;

import java.util.HashSet;

public class Main {

  public static void main(String[] args) {
    MonteCarloAgent mc1 = new MonteCarloAgent(0.05);
    RandomAgent r1 = new RandomAgent();
    runExperiment(mc1, r1, 50000);

    RandomAgent r2 = new RandomAgent();
    RandomAgent r3 = new RandomAgent();
    runExperiment(r2, r3, 50000);
  }

  private static void runExperiment(Agent a1, Agent a2, int numGames) {
    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGame game;
    
    for (int i = 0 ; i < numGames ; i++) {
      game = new TicTacToeGame(a1, a2);
      int winner = game.play();

      if (winner == -1) {
        System.out.println("ERROR");
      }

      wins[winner]++;
    }

    System.out.println("Draw   : " + wins[0]);
    System.out.println(a1.getName() + " : " + wins[1]);
    System.out.println(a2.getName() + " : " + wins[2]);
    System.out.println();
  }
}
