package com.games.findepsilon;

import com.opencsv.CSVWriter;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.general.Agent;
import com.games.tictactoe.TicTacToeResults;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;


/** Main class for making an agent play Find Epsilon. */
public class Main {

  private static final String RESULTS = "./results.csv";

  public static void main(String[] args) throws IOException {
    MonteCarloAgent mc = new MonteCarloAgent(0.1 /* epsilon */);
    
    // Manually test all values of epsilon
    FindEpsilonGame game = new FindEpsilonGame(mc);
    
    try (
      Writer writer = Files.newBufferedWriter(Paths.get(RESULTS));

      CSVWriter csvWriter = new CSVWriter(writer,
              CSVWriter.DEFAULT_SEPARATOR,
              CSVWriter.NO_QUOTE_CHARACTER,
              CSVWriter.DEFAULT_ESCAPE_CHARACTER,
              CSVWriter.DEFAULT_LINE_END);
    ) {
      // Headers of CSV file
      String[] headerRecord = {"Epsilon", "Wins", "Losses", "Draws"};
      csvWriter.writeNext(headerRecord);

      for (double epsilon = 0.01 ; epsilon < 1.0 ; epsilon += 0.01) {
        TicTacToeResults results = game.playTicTacToe(epsilon, 10000);
        
        // Write results to file
        csvWriter.writeNext(new String[] {
          String.valueOf(epsilon),
          String.valueOf(results.wins),
          String.valueOf(results.losses),
          String.valueOf(results.draws)});
      }
    }
    
    //runExperiment(mc, 10000 /* number of games */);
    //
    //RandomAgent rand = new RandomAgent();
    //runExperiment(rand, 10000 /* number of games */);
  }
  
  /**
   * Makes the given agent play the game the given number of times and prints
   * the results to the console.
   *
   * @param agent    the player
   * @param numGames the number of games to be played
   */
  private static void runExperiment(Agent agent, int numGames) {
    FindEpsilonGame game;
    
    for (int i = 0 ; i < numGames ; i++) {
      game = new FindEpsilonGame(agent);
      game.play(); // ignoring the returned value until it is given a meaning
    }
  }
}
