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

  private static final String RESULTS =
      "./ChungToiEpsilonResults_1kGames.csv";

  public static void main(String[] args) throws IOException {
    saveEpsilonResultsInCSV(
        0.01,    /* epsilon granularity */
        1*1000  /* number of games */);
  }

  /**
   * Makes a Monte Carlo agent play Chung TOi against a random agent the given
   * number of times, repeating for each value of epsilon from 0 to 1 (not
   * inclusive) and saves the results in a CSV file.
   *
   * @param epsilonGranularity difference between consecutive epsilon values
   *                           tried
   * @param numGames           number of games to play for each epsilon value
   */
  private static void saveEpsilonResultsInCSV(
      double epsilonGranularity,
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

      int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
      ChungToiGame game = null;

      for (double epsilon = epsilonGranularity ;
           epsilon < 1.0 ;
           epsilon += epsilonGranularity) {

        System.out.println("epsilon: " + epsilon);

        MonteCarloAgent monteCarloAgent = new MonteCarloAgent(epsilon);
        RandomAgent randomAgent = new RandomAgent();

        for (int i = 1 ; i <= numGames ; i++) {
          System.out.println("game #" + i);
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
