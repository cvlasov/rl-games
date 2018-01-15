package tictactoe;

import agents.MonteCarloAgent;
import agents.RandomAgent;
import general.Agent;

public class Main {

  public static void main(String[] args) {
    MonteCarloAgent mc1 = new MonteCarloAgent(0.05);
    RandomAgent r1 = new RandomAgent();
    runExperiment(mc1, r1, 1);

//    RandomAgent r2 = new RandomAgent();
//    RandomAgent r3 = new RandomAgent();
//    runExperiment(r2, r3, 50000);

    //testStateEquality();
  }

  private static void runExperiment(Agent a1, Agent a2, int numGames) {
    TicTacToeGame game = new TicTacToeGame(a1, a2);
    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2

    for (int i = 0 ; i < numGames ; i++) {
      game.initialize();
      int winner = game.playOneGame();

      if (winner == -1) {
        System.out.println("ERROR");
      }

      wins[winner]++;
    }

    System.out.println("Draw   : " + wins[0]);
    System.out.println("Agent1 : " + wins[1]);
    System.out.println("Agent2 : " + wins[2]);
    System.out.println();
  }

  private static void testStateEquality() {
    TicTacToeState state1 = new TicTacToeState();
    //state1.applyAction(new TicTacToeAction(0, TicTacToeGame.TokenType.X));

    TicTacToeState state2 = new TicTacToeState();

    System.out.println(state1.equals(state2));
  }
}
