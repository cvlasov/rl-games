package com.games.experiments;

import static com.games.experiments.ExperimentHelper.GameType;
import static com.games.experiments.ExperimentHelper.savePerformanceResultsInCSV;

import java.io.IOException;

/**
 * Main class for making agents play Nim with exploring starts against each
 * other multiple times while recording the win/loss/draw rate as the number of
 * games played so far increases.
 */
public final class NimExploringStartsPerformanceExperiment {

  public static void main(String[] args) throws IOException {
    savePerformanceResultsInCSV(GameType.NIM_ES,
                                99.99,    /* epsilon - IGNORED FOR ES */
                                100*1000, /* number of games */
                                1*1000,   /* result interval */
                                false     /* whether to print debugging
                                             stmts */);
  }
}
