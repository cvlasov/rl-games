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
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main class for making agents play two-player games against each other
 * multiple times while recording the win/loss/draw rate as the number of
 * games played so far increases.
 */
public final class PerformanceExperiments {

  private static final String PERFORMANCE_RESULTS_FILE_NAME =
      "./%s_PerformanceResults_Epsilon%.2f_10kGames_WithPolicySize.csv";

  private static final String NUM_GAMES_HEADER = "NumGames";
  private static final String WIN_HEADER  ="%sWin";
  private static final String LOSS_HEADER ="%sLoss";
  private static final String DRAW_HEADER ="%sDraw";
  private static final String POLICY_STATES_HEADER = "StatesInPolicy";

  public static void main(String[] args) throws IOException {
    savePerformanceResultsInCSV(GameType.TIC_TAC_TOE_NORMAL,
                                0.10, /* epsilon */
                                10*1000,  /* number of games */
                                1000    /* result interval */);
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
   */
  private static void savePerformanceResultsInCSV(
      GameType type,
      double epsilon,
      int numGames,
      int resultInterval) throws IOException {

    String fileName = String.format(PERFORMANCE_RESULTS_FILE_NAME,
                                    type.toString(),
                                    epsilon);

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
      MonteCarloAgent mcAgent = new MonteCarloAgent(epsilon);
      RandomAgent randAgent = new RandomAgent();

      for (int gamesSoFar = 1 ; gamesSoFar <= numGames ; gamesSoFar++) {
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
