package com.games.experiments;

import static com.games.experiments.ExperimentHelper.GameType;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.chungtoi.ChungToiGame;
import com.games.general.Game;
import com.games.tictactoe.TicTacToeGameWithLimitedActions;
import com.games.tictactoe.TicTacToeGameWithSymmetricEquality;
import com.games.tictactoe.TicTacToeNormalGame;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main class for making agents play two-player games against each other
 * multiple times while varying the learning parameter to determine the best
 * parameter values.
 */
public final class EpsilonExperiments {

  private static boolean debug = false;

  private static final String EPSILON_RESULTS_FILE_NAME =
      "./%s_EpsilonResults_10kGames.csv";

  private static final String EPSILON_HEADER = "Epsilon";
  private static final String WIN_HEADER  ="%sWin";
  private static final String LOSS_HEADER ="%sLoss";
  private static final String DRAW_HEADER ="%sDraw";

  public static void main(String[] args) throws IOException {
    saveEpsilonResultsInCSV(GameType.TIC_TAC_TOE_NORMAL,
                            0.5,     /* epsilon precision */
                            10*1000  /* number of games */);
  }

  /**
   * Makes a Monte Carlo agent play the given type of game against a random
   * agent the given number of times, repeating for each value of epsilon from
   * 0 to 1 (inclusive) and saves the results in a CSV file.
   *
   * @param type             type of two-player game to play
   * @param epsilonPrecision difference between consecutive values tried
   * @param numGames         number of games to play
   */
  private static void saveEpsilonResultsInCSV(
      GameType type,
      double epsilonPrecision,
      int numGames) throws IOException {

    String fileName = String.format(EPSILON_RESULTS_FILE_NAME, type.toString());

    try (
      CSVWriter csvWriter = new CSVWriter(
          Files.newBufferedWriter(Paths.get(fileName)),
          CSVWriter.DEFAULT_SEPARATOR,
          CSVWriter.NO_QUOTE_CHARACTER,
          CSVWriter.DEFAULT_ESCAPE_CHARACTER,
          CSVWriter.DEFAULT_LINE_END);
    ) {
      // Headers of CSV file
      String[] headerRecord = new String[] {
          EPSILON_HEADER,
          String.format(WIN_HEADER,  type.toString()),
          String.format(LOSS_HEADER, type.toString()),
          String.format(DRAW_HEADER, type.toString())
        };

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

        int[] wins = new int[3];  // index 0 is draw, 1 is agent1, 2 is agent2
        Game game = null;
        MonteCarloAgent mcAgent = new MonteCarloAgent(epsilon);
        RandomAgent randAgent = new RandomAgent();

        for (int gameNum = 1 ; gameNum <= numGames ; gameNum++) {
          if (debug) {
            System.out.println();
            System.out.println("-------------------------------------------");
            System.out.println("GAME #" + gameNum);
            System.out.println("-------------------------------------------");
            System.out.println();
          }

          switch (type) {
            case CHUNG_TOI:
              game = new ChungToiGame(mcAgent, randAgent);
              break;
            case TIC_TAC_TOE_NORMAL:
              game = new TicTacToeNormalGame(mcAgent, randAgent);
              break;
            case TIC_TAC_TOE_LIMITED_ACTIONS:
              game = new TicTacToeGameWithLimitedActions(mcAgent, randAgent);
              break;
            case TIC_TAC_TOE_SYMMETRIC_EQUALITY:
              game = new TicTacToeGameWithSymmetricEquality(mcAgent, randAgent);
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
