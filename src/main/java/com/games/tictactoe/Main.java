package com.games.tictactoe;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.general.Agent;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

/** Main class for making agents play Tic-Tac-Toe against each other. */
public class Main {

  private static final String RESULTS = "./learningresults.csv";

  public static void main(String[] args) throws IOException {
    MonteCarloAgent mc1 = new MonteCarloAgent(0.14 /* epsilon */);
    RandomAgent r1 = new RandomAgent();
    MonteCarloAgent mc2 = new MonteCarloAgent(0.14 /* epsilon */);
    RandomAgent r2 = new RandomAgent();
    produceExperimentResults(mc1, r1, mc2, r2, 50000 /* number of games */);

    //RandomAgent r2 = new RandomAgent();
    //RandomAgent r3 = new RandomAgent();
    //runExperiment(r2, r3, 50000 /* number of games */);
  }

  private static void produceExperimentResults(Agent agent1, Agent agent2, Agent agent3, Agent agent4, int numGames)
      throws IOException {
    int[] wins1 = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    int[] wins2 = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGame game1;
    TicTacToeGameWithLimitedActions game2;

    try (
      Writer writer = Files.newBufferedWriter(Paths.get(RESULTS));

      CSVWriter csvWriter = new CSVWriter(writer,
              CSVWriter.DEFAULT_SEPARATOR,
              CSVWriter.NO_QUOTE_CHARACTER,
              CSVWriter.DEFAULT_ESCAPE_CHARACTER,
              CSVWriter.DEFAULT_LINE_END);
    ) {
      // Headers of CSV file
      String[] headerRecord = {"NumGames",
                               "NormalWin",
                               "NormalLoss",
                               "NormalDraw",
                               "NormalWinRate",
                               "LimitedActionsWin",
                               "LimitedActionsLoss",
                               "LimitedActionsDraw",
                               "LimitedActionsWinRate"};
      csvWriter.writeNext(headerRecord);

      for (int gamesSoFar = 1 ; gamesSoFar <= numGames ; gamesSoFar++) {
        game1 = new TicTacToeGame(agent1, agent2);
        game2 = new TicTacToeGameWithLimitedActions(agent3, agent4);
        int winner1 = game1.play();
        int winner2 = game2.play();

        if (winner1 == -1 || winner2 == -1) {
          System.out.println("ERROR");
        }

        wins1[winner1]++;
        wins2[winner2]++;

        if (gamesSoFar % 1000 == 0) {
          csvWriter.writeNext(new String[] {
            String.valueOf(gamesSoFar),
            String.valueOf(wins1[1]),
            String.valueOf(wins1[2]),
            String.valueOf(wins1[0]),
            String.valueOf((double) wins1[1] / (wins1[0] + wins1[1] + wins1[2])),
            String.valueOf(wins2[1]),
            String.valueOf(wins2[2]),
            String.valueOf(wins2[0]),
            String.valueOf((double) wins2[1] / (wins2[0] + wins2[1] + wins2[2])),
          });
        }
      }

    // System.out.println("Draw   : " + wins[0]);
    // System.out.println(agent1.getName() + " : " + wins[1]);
    // System.out.println(agent2.getName() + " : " + wins[2]);
    // System.out.println();
    }
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

  private static void runExperimentWithSymmetry(Agent agent1, Agent agent2, int numGames) {
    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGameWithSymmetry game;

    for (int i = 0 ; i < numGames ; i++) {
      game = new TicTacToeGameWithSymmetry(agent1, agent2);
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

    private static void runExperimentWithLimitedActions(Agent agent1, Agent agent2, int numGames) {
    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGameWithLimitedActions game;

    for (int i = 0 ; i < numGames ; i++) {
      game = new TicTacToeGameWithLimitedActions(agent1, agent2);
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
