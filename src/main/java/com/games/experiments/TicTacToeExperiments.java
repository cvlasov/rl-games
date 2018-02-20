package com.games.experiments;

import static com.games.tictactoe.TicTacToeHelper.GameType;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.general.Agent;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Main class for making agents play Tic-Tac-Toe against each other. */
public final class TicTacToeExperiments {

  private static final String COMBINED_RESULTS =
      "./CombinedResults(e0.1).csv";

  private static final String NORMAL_RESULTS =
      "./NormalResults(e0.1).csv";

  private static final String LIMITED_ACTIONS_RESULTS =
      "./LimitedActionsResults(e0.07).csv";

  private static final String SYMMETRIC_EQUALITY_RESULTS =
      "./LimitedActionsResults(e0.07).csv";

  public static void main(String[] args) throws IOException {
    MonteCarloAgent mc1 = new MonteCarloAgent(0.1 /* epsilon */);
    RandomAgent r1 = new RandomAgent();

    produceCombinedResults(new MonteCarloAgent(0.1),
                           new RandomAgent(),
                           new MonteCarloAgent(0.1),
                           new RandomAgent(),
                           new MonteCarloAgent(0.1),
                           new RandomAgent(),
                           500000,  /* number of games */
                           10000    /* result interval */);

    // runExperiment(GameType.SYMMETRIC_EQUALITY,
    //               mc1,
    //               r1,
    //               10000 /* number of games */);
    //
    // produceExperimentResults(GameType.SYMMETRIC_EQUALITY,
    //                          mc1,
    //                          r1,
    //                          100, /* number of games */
    //                          10   /* result interval */);
  }

  /**
   * Makes the two given agents play the given type of game against each other
   * the given number of times and prints the results to the console.
   *
   * @param gameType type of Tic-Tac-Toe to play
   * @param agent1   first player
   * @param agent2   second player
   * @param numGames number of games the agents will play against each other
   */
  private static void runExperiment(
      GameType gameType,
      Agent agent1,
      Agent agent2,
      int numGames) {

    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGame game = null;

    for (int i = 0 ; i < numGames ; i++) {
      switch (gameType) {
        case NORMAL:
          game = new TicTacToeNormalGame(agent1, agent2);
          break;
        case LIMITED_ACTIONS:
          game = new TicTacToeGameWithLimitedActions(agent1, agent2);
          break;
        case SYMMETRIC_EQUALITY:
          game = new TicTacToeGameWithSymmetricEquality(agent1, agent2);
          break;
      }

      int winner = game.play();
      if (winner == -1) System.out.println("ERROR");
      wins[winner]++;
    }

    printResults(agent1, agent2, wins);
  }

  /**
   * Makes the two given agents play the given type of game against each other
   * the given number of times and saves the results in a CSV file.
   *
   * @param gameType       type of Tic-Tac-Toe to play
   * @param agent1         first player
   * @param agent2         second player
   * @param numGames       number of games the agents play against each other
   * @param resultInterval number of games between datapoints saved to CSV file
   */
  private static void produceExperimentResults(
      GameType gameType,
      Agent agent1,
      Agent agent2,
      int numGames,
      int resultInterval) throws IOException {

    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGame game = null;

    try (
      Writer writer =
            (gameType == GameType.NORMAL) ? Files.newBufferedWriter(Paths.get(NORMAL_RESULTS))
          : (gameType == GameType.LIMITED_ACTIONS) ? Files.newBufferedWriter(Paths.get(LIMITED_ACTIONS_RESULTS))
          : (gameType == GameType.SYMMETRIC_EQUALITY) ? Files.newBufferedWriter(Paths.get(SYMMETRIC_EQUALITY_RESULTS))
          : null;

      CSVWriter csvWriter = new CSVWriter(writer,
              CSVWriter.DEFAULT_SEPARATOR,
              CSVWriter.NO_QUOTE_CHARACTER,
              CSVWriter.DEFAULT_ESCAPE_CHARACTER,
              CSVWriter.DEFAULT_LINE_END);
    ) {
      // Headers of the CSV file
      String[] headerRecord = null;

      switch (gameType) {
        case NORMAL:
          headerRecord = new String[] {"NumGames",
                                       "NormalWin",
                                       "NormalLoss",
                                       "NormalDraw"};
          break;
        case LIMITED_ACTIONS:
          headerRecord = new String[] {"NumGames",
                                       "LimitedActionsWin",
                                       "LimitedActionsLoss",
                                       "LimitedActionsDraw"};
          break;
        case SYMMETRIC_EQUALITY:
          headerRecord = new String[] {"NumGames",
                                       "SymmetricEqualityWin",
                                       "SymmetricEqualityLoss",
                                       "SymmetricEqualityDraw"};
          break;
      }

      csvWriter.writeNext(headerRecord);

      for (int gamesSoFar = 1 ; gamesSoFar <= numGames ; gamesSoFar++) {
        switch (gameType) {
          case NORMAL:
            game = new TicTacToeNormalGame(agent1, agent2);
            break;
          case LIMITED_ACTIONS:
            game = new TicTacToeGameWithLimitedActions(agent1, agent2);
            break;
          case SYMMETRIC_EQUALITY:
            game = new TicTacToeGameWithSymmetricEquality(agent1, agent2);
            break;
        }

        int winner = game.play();
        if (winner == -1) System.out.println("ERROR");
        wins[winner]++;

        if (gamesSoFar % resultInterval == 0)
          csvWriter.writeNext(new String[] {
            String.valueOf(gamesSoFar),
            String.valueOf(wins[1]),
            String.valueOf(wins[2]),
            String.valueOf(wins[0]),
          });
      }
    }
  }

  /**
   * Makes the each of the three given pairs of agents play each of the three
   * types of games the given number of times against each other and saves the
   * results in a CSV file.
   *
   * @param gameType type of Tic-Tac-Toe to play
   * @param normalAgent1              first player of normal game
   * @param normalAgent2              second player of normal game
   * @param limitedActionsAgent1      first player of limited actions game
   * @param limitedActionsAgent2      second player of limited actions game
   * @param symmetricEqualityAgent1   first player of symmetric equality game
   * @param symmetricEqualityAgent2   second player of symmetric equality game
   * @param numGames                  number of games each pair of agents will
   *                                  play against each other
   */
  private static void produceCombinedResults(
      Agent normalAgent1,
      Agent normalAgent2,
      Agent limitedActionsAgent1,
      Agent limitedActionsAgent2,
      Agent symmetricEqualityAgent1,
      Agent symmetricEqualityAgent2,
      int numGames,
      int resultInterval)
      throws IOException {

    // index 0 is draw, 1 is agent1, 2 is agent2
    int[] normalWins = new int[3];
    int[] limitedActionsWins = new int[3];
    int[] symmetricEqualityWins = new int[3];

    TicTacToeNormalGame normalGame;
    TicTacToeGameWithLimitedActions limitedActionsGame;
    TicTacToeGameWithSymmetricEquality symmetricEqualityGame;

    try (
      Writer writer = Files.newBufferedWriter(Paths.get(COMBINED_RESULTS));

      CSVWriter csvWriter = new CSVWriter(writer,
              CSVWriter.DEFAULT_SEPARATOR,
              CSVWriter.NO_QUOTE_CHARACTER,
              CSVWriter.DEFAULT_ESCAPE_CHARACTER,
              CSVWriter.DEFAULT_LINE_END);
    ) {
      // Headers of the CSV file
      String[] headerRecord = {"NumGames",
                               "NormalWin",
                               "NormalLoss",
                               "NormalDraw",
                               "NormalWinRate",
                               "LimitedActionsWin",
                               "LimitedActionsLoss",
                               "LimitedActionsDraw",
                               "LimitedActionsWinRate",
                               "SymmetricEqualityWin",
                               "SymmetricEqualityLoss",
                               "SymmetricEqualityDraw",
                               "SymmetricEqualityWinRate"};
      csvWriter.writeNext(headerRecord);

      for (int gamesSoFar = 1 ; gamesSoFar <= numGames ; gamesSoFar++) {
        normalGame = new TicTacToeNormalGame(normalAgent1, normalAgent2);
        limitedActionsGame =
            new TicTacToeGameWithLimitedActions(limitedActionsAgent1, limitedActionsAgent2);
        symmetricEqualityGame =
            new TicTacToeGameWithSymmetricEquality(symmetricEqualityAgent1, symmetricEqualityAgent1);

        int normalWinner = normalGame.play();
        int limitedActionsWinner = limitedActionsGame.play();
        int symmetricEqualityWinner = symmetricEqualityGame.play();

        if (normalWinner == -1
            || limitedActionsWinner == -1
            || symmetricEqualityWinner == -1) {
          System.out.println("ERROR");
        }

        normalWins[normalWinner]++;
        limitedActionsWins[limitedActionsWinner]++;
        symmetricEqualityWins[symmetricEqualityWinner]++;

        if (gamesSoFar % resultInterval == 0) {
          csvWriter.writeNext(new String[] {
            String.valueOf(gamesSoFar),
            String.valueOf(normalWins[1]),
            String.valueOf(normalWins[2]),
            String.valueOf(normalWins[0]),
            String.valueOf(getWinRate(normalWins)),
            String.valueOf(limitedActionsWins[1]),
            String.valueOf(limitedActionsWins[2]),
            String.valueOf(limitedActionsWins[0]),
            String.valueOf(getWinRate(limitedActionsWins)),
            String.valueOf(symmetricEqualityWins[1]),
            String.valueOf(symmetricEqualityWins[2]),
            String.valueOf(symmetricEqualityWins[0]),
            String.valueOf(getWinRate(symmetricEqualityWins))
          });
        }
      }
    }
  }

  /**
   * Computes the proportion of the games that agent 1 won.
   *
   * @param wins array of size three containing the number of draws and the
   *             number of times each agent won
   * @returns agent 1's win rate
   */
  private static double getWinRate(int[] wins /* must be of size 3 */) {
    return (double) wins[1] / (wins[0] + wins[1] + wins[2]);
  }

  /** Prints the number of draws and wins for each player to the console. */
  private static void printResults(Agent agent1, Agent agent2, int[] wins) {
    System.out.println("Draw   : " + wins[0]);
    System.out.println(agent1.getName() + " : " + wins[1]);
    System.out.println(agent2.getName() + " : " + wins[2]);
    System.out.println();
  }
}
