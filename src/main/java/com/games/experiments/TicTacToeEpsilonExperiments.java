package com.games.experiments;

import static com.games.tictactoe.TicTacToeHelper.GameType;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.general.Agent;
import com.games.tictactoe.TicTacToeGame;
import com.games.tictactoe.TicTacToeGameWithLimitedActions;
import com.games.tictactoe.TicTacToeGameWithSymmetricEquality;
import com.games.tictactoe.TicTacToeNormalGame;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Main class for making agents play Tic-Tac-Toe against each other. */
public final class TicTacToeEpsilonExperiments {

  private static boolean debug = false;

  private static final String NORMAL_EPSILON_RESULTS =
      "./TicTacToeNormal_EpsilonResults_1MillionGames.csv";

  private static final String LIMITED_ACTIONS_EPSILON_RESULTS =
      "./TicTacToeLimitedActions_EpsilonResults_1MillionGames.csv";

  private static final String SYMMETRIC_EQUALITY_EPSILON_RESULTS =
      "./TicTacToeSymmetricEquality_EpsilonResults_1MillionGames.csv";

  public static void main(String[] args) throws IOException {
    saveEpsilonResultsForSingleGameTypeInCSV(
        GameType.SYMMETRIC_EQUALITY,
        0.01,      /* epsilon precision */
        1000*1000  /* number of games */);
  }

  /**
   * Makes a Monte Carlo agent play each the given type of games against a
   * random agent the given number of times, repeating for each value of epsilon
   * from 0 to 1 (inclusive) and saves the results in a CSV file.
   *
   * @param gameType         type of Tic-Tac-Toe game to play
   * @param epsilonPrecision difference between consecutive values tried
   * @param numGames         number of games to play
   */
  private static void saveEpsilonResultsForSingleGameTypeInCSV(
      GameType gameType,
      double epsilonPrecision,
      int numGames) throws IOException {

    try (
      Writer writer =
            (gameType == GameType.NORMAL) ?
                Files.newBufferedWriter(Paths.get(NORMAL_EPSILON_RESULTS))
          : (gameType == GameType.LIMITED_ACTIONS) ?
                Files.newBufferedWriter(Paths.get(LIMITED_ACTIONS_EPSILON_RESULTS))
          : (gameType == GameType.SYMMETRIC_EQUALITY) ?
                Files.newBufferedWriter(Paths.get(SYMMETRIC_EQUALITY_EPSILON_RESULTS))
          : null;

      CSVWriter csvWriter = new CSVWriter(
          writer,
          CSVWriter.DEFAULT_SEPARATOR,
          CSVWriter.NO_QUOTE_CHARACTER,
          CSVWriter.DEFAULT_ESCAPE_CHARACTER,
          CSVWriter.DEFAULT_LINE_END);
    ) {
      // Headers of CSV file
      String[] headerRecord = null;

      switch (gameType) {
        case NORMAL:
          headerRecord = new String[] {"Epsilon",
                                       "NormalWin",
                                       "NormalLoss",
                                       "NormalDraw"};
          break;
        case LIMITED_ACTIONS:
          headerRecord = new String[] {"Epsilon",
                                       "LimitedActionsWin",
                                       "LimitedActionsLoss",
                                       "LimitedActionsDraw"};
          break;
        case SYMMETRIC_EQUALITY:
          headerRecord = new String[] {"Epsilon",
                                       "SymmetricEqualityWin",
                                       "SymmetricEqualityLoss",
                                       "SymmetricEqualityDraw"};
          break;
      }

      csvWriter.writeNext(headerRecord);

      for (double epsilon = 0.0 ;
           epsilon <= 1.0 ;
           epsilon += epsilonPrecision) {

        if (debug) {
          System.out.println();
          System.out.println("-------------------------------------------");
          System.out.println("-------------------------------------------");
          System.out.println("NEW EPSILON VALUE: " + epsilon);
        }

        int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
        TicTacToeGame game = null;
        MonteCarloAgent monteCarloAgent = new MonteCarloAgent(epsilon);
        RandomAgent randomAgent = new RandomAgent();

        for (int i = 1 ; i <= numGames ; i++) {
          if (debug) {
            System.out.println();
            System.out.println("-------------------------------------------");
            System.out.println("GAME #" + i);
            System.out.println("-------------------------------------------");
            System.out.println();
          }

          switch (gameType) {
            case NORMAL:
              game = new TicTacToeNormalGame(monteCarloAgent, randomAgent);
              break;
            case LIMITED_ACTIONS:
              game = new TicTacToeGameWithLimitedActions(monteCarloAgent, randomAgent);
              break;
            case SYMMETRIC_EQUALITY:
              game = new TicTacToeGameWithSymmetricEquality(monteCarloAgent, randomAgent);
              break;
          }

          int winner = game.play();
          if (winner == -1) System.out.println("ERROR");
          wins[winner]++;
        }

        csvWriter.writeNext(new String[] {
          String.valueOf(epsilon),
          String.valueOf(wins[1]),
          String.valueOf(wins[2]),
          String.valueOf(wins[0])});
       }
     }
  }
}
