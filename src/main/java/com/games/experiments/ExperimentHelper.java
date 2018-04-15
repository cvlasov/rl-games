package com.games.experiments;

import com.games.agents.MonteCarloAgent;
import com.games.agents.MonteCarloESAgent;
import com.games.agents.RandomAgent;
import com.games.chungtoi.ChungToiGame;
import com.games.general.Game;
import com.games.general.State;
import com.games.nim.NimGame;
import com.games.nim.NimGameES;
import com.games.tictactoe.TicTacToeGameWithLimitedActions;
import com.games.tictactoe.TicTacToeGameWithSymmetricEquality;
import com.games.tictactoe.TicTacToeNormalGame;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class ExperimentHelper {

  private static final String EPSILON_RESULTS_FILE_NAME =
      "./%s_EpsilonResults_%dGames_WithPolicySize.csv";

  private static final String PERFORMANCE_RESULTS_FILE_NAME =
      "./%s_PerformanceResults_Epsilon%.2f_%dGames_WithPolicySize.csv";

  private static final String EPSILON_HEADER = "Epsilon";
  private static final String NUM_GAMES_HEADER = "NumGames";
  private static final String WIN_HEADER  ="%sWin";
  private static final String LOSS_HEADER ="%sLoss";
  private static final String DRAW_HEADER ="%sDraw";
  private static final String POLICY_STATES_HEADER = "StatesInPolicy";

  private ExperimentHelper() {}  // restrict instantiation

  enum GameType {
    CHUNG_TOI ("ChungToi"),
    NIM ("Nim"),
    NIM_ES ("NimExploringStarts"),
    TIC_TAC_TOE_NORMAL ("TicTacToeNormal"),
    TIC_TAC_TOE_LIMITED_ACTIONS ("TicTacToeLimitedActions"),
    TIC_TAC_TOE_SYMMETRIC_EQUALITY ("TicTacToeSymmetricEquality");

    private final String name;

    GameType(String s) {
      name = s;
    }

    public String toString() {
      return this.name;
    }
  }

  /**
   * Makes a Monte Carlo agent play the given type of game against a random
   * agent the given number of times, repeating for each value of epsilon in the
   * given range (inclusive) and saves the results in a CSV file.
   *
   * @param type             type of two-player game to play
   * @param epsilonStart     smallest epsilon value to try
   * @param epsilonEnd       largest epsilon value to try
   * @param epsilonPrecision difference between consecutive values tried
   * @param numGames         number of games to play
   * @param debug            whether or not to print debugging statements to the
   *                         console
   */
  static void saveEpsilonResultsInCSV(
      GameType type,
      double epsilonStart,
      double epsilonEnd,
      double epsilonPrecision,
      int numGames,
      boolean debug) throws IOException {

    String fileName = String.format(EPSILON_RESULTS_FILE_NAME,
                                    type.toString(),
                                    numGames);

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
          String.format(DRAW_HEADER, type.toString()),
          POLICY_STATES_HEADER
        };

      csvWriter.writeNext(headerRecord);

      iterate_epsilon:
      for (double epsilon = epsilonStart ;
           epsilon <= epsilonEnd + (epsilonPrecision/10);  // for floating-point error
           epsilon += epsilonPrecision) {

        if (debug) {
          System.out.println();
          System.out.println("-------------------------------------------");
          System.out.println("-------------------------------------------");
          System.out.println("NEW EPSILON VALUE: " + epsilon);
        }

        if (epsilon > epsilonEnd) {  // due to floating-point error
          epsilon = epsilonEnd;
        }

        int[] wins = new int[3];  // index 0 is draw, 1 is agent1, 2 is agent2
        Game game = null;
        MonteCarloAgent mcAgent = new MonteCarloAgent(epsilon, debug);
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
            case NIM:
              game = new NimGame(mcAgent, randAgent);
              break;
            case NIM_ES:
              break iterate_epsilon;
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
          String.valueOf(wins[0]),
          String.valueOf(mcAgent.getPolicy().size())});
       }
     }
  }

  /**
   * Makes a Monte Carlo agent play the given type of game against a random
   * agent the given number of times, saving the win/loss/draw rate in a CSV
   * file at regular intervals.
   *
   * @param type           type of two-player game to play
   * @param epsilon        value of epsilon to use for the Monte Carlo agent
   * @param numGames       number of games the agents play against each other
   * @param resultInterval number of games between datapoints saved to CSV file
   * @param debug          whether or not to print debugging statements to the
   *                       console
   */
  static void savePerformanceResultsInCSV(
      GameType type,
      double epsilon,
      int numGames,
      int resultInterval,
      boolean debug) throws IOException {

    String fileName = String.format(PERFORMANCE_RESULTS_FILE_NAME,
                                    type.toString(),
                                    epsilon,
                                    numGames);

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
          NUM_GAMES_HEADER,
          String.format(WIN_HEADER,  type.toString()),
          String.format(LOSS_HEADER, type.toString()),
          String.format(DRAW_HEADER, type.toString()),
          POLICY_STATES_HEADER
        };

      csvWriter.writeNext(headerRecord);

      int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
      Game game = null;
      MonteCarloAgent mcAgent = new MonteCarloAgent(epsilon, debug);
      MonteCarloESAgent mcAgentES = new MonteCarloESAgent(debug);
      RandomAgent randAgent = new RandomAgent();

      for (int gamesSoFar = 1 ; gamesSoFar <= numGames ; gamesSoFar++) {
        switch (type) {
          case CHUNG_TOI:
            game = new ChungToiGame(mcAgent, randAgent);
            break;
          case NIM:
            game = new NimGame(mcAgent, randAgent);
            break;
          case NIM_ES:
            game = new NimGameES(mcAgentES, randAgent);
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

        if (gamesSoFar % resultInterval == 0)
          csvWriter.writeNext(new String[] {
            String.valueOf(gamesSoFar),
            String.valueOf(wins[1]),
            String.valueOf(wins[2]),
            String.valueOf(wins[0]),
            String.valueOf(mcAgent.getPolicy().size())
          });
      }
    }
  }
}
