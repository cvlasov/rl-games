package com.games.experiments;

import static com.games.experiments.ExperimentHelper.GameType;
import static com.games.experiments.ExperimentHelper.savePerformanceResultsInCSV;

import java.io.IOException;

/**
 * Main class for making agents play Nim against each other multiple times
 * while recording the win/loss/draw rate as the number of games played so far
 * increases.
 */
public final class NimPerformanceExperiment {

  public static void main(String[] args) throws IOException {
    savePerformanceResultsInCSV(GameType.NIM,
                                0.05,      /* epsilon */
                                1000*1000, /* number of games */
                                10*1000,   /* result interval */
                                false      /* whether to print debugging
                                              stmts */);
  }
}
