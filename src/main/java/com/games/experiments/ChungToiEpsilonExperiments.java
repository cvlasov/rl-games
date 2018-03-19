package com.games.experiments;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.general.Agent;
import com.games.chungtoi.ChungToiGame;

import com.opencsv.CSVWriter;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Main class for making agents play Chung Toi against each other. */
public class ChungToiEpsilonExperiments {

  private static boolean debug = false;

  private static final String RESULTS =
      "./ChungToi_EpsilonResults_1MillionGames.csv";

  public static void main(String[] args) throws IOException {
    saveEpsilonResultsInCSV(
        0.01,      /* epsilon precision */
        1000*1000  /* number of games */);
  }

  /**
   * Makes a Monte Carlo agent play Chung Toi against a random agent the given
   * number of times, repeating for each value of epsilon from 0 to 1
   * (inclusive) and saves the results in a CSV file.
   *
   * @param epsilonPrecision difference between consecutive values tried
   * @param numGames         number of games to play for each epsilon value
   */
  private static void saveEpsilonResultsInCSV(
      double epsilonPrecision,
      int numGames) throws IOException {

    try (
      Writer writer = Files.newBufferedWriter(Paths.get(RESULTS));

      CSVWriter csvWriter = new CSVWriter(
          writer,
          CSVWriter.DEFAULT_SEPARATOR,
          CSVWriter.NO_QUOTE_CHARACTER,
          CSVWriter.DEFAULT_ESCAPE_CHARACTER,
          CSVWriter.DEFAULT_LINE_END);
    ) {
      // Headers of CSV file
      String[] headerRecord = {"Epsilon", "Win", "Loss", "Draw"};
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
        ChungToiGame game = null;
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

          game = new ChungToiGame(monteCarloAgent, randomAgent);
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
