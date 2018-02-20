package com.games.experiments;

import com.opencsv.CSVWriter;

import com.games.tictactoe.TicTacToeResults;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * Main class for running experiments related to the value of epsilon used
 * by a Monte Carlo agent when playing Tic-Tac-Toe.
 */
public final class Main {

  private static final String RESULTS = "./NormalEpsilonResults(50kGames).csv";
  private static final double EPSILON_GRANULARITY = 0.01;
  private static final int NUM_GAMES = 50000;

  public static void main(String[] args) throws IOException {
    try (
      Writer writer = Files.newBufferedWriter(Paths.get(RESULTS));

      CSVWriter csvWriter = new CSVWriter(writer,
              CSVWriter.DEFAULT_SEPARATOR,
              CSVWriter.NO_QUOTE_CHARACTER,
              CSVWriter.DEFAULT_ESCAPE_CHARACTER,
              CSVWriter.DEFAULT_LINE_END);
    ) {
      // Headers of CSV file
      String[] headers = { "Epsilon", "Wins", "Losses", "Draws" };
      csvWriter.writeNext(headers);

      for (double epsilon = EPSILON_GRANULARITY ;
           epsilon < 1.0 ;
           epsilon += EPSILON_GRANULARITY) {
        TicTacToeResults results =
            Experiments.playTicTacToe(epsilon, NUM_GAMES);

        // Write results to file
        csvWriter.writeNext(new String[] {
          String.valueOf(epsilon),
          String.valueOf(results.wins),
          String.valueOf(results.losses),
          String.valueOf(results.draws)});
      }
    }
  }
}
