package com.games.experiments;

import static com.games.experiments.ExperimentHelper.GameType;
import static com.games.experiments.ExperimentHelper.saveEpsilonResultsInCSV;

import java.io.IOException;

/**
 * Main class for making agents play Nim against each other multiple times
 * while varying the learning parameter to determine the best parameter values.
 */
public final class NimEpsilonExperiment {

  public static void main(String[] args) throws IOException {
    saveEpsilonResultsInCSV(GameType.NIM,
                            0.01,      /* epsilon precision */
                            1000*1000, /* number of games */
                            false      /* whether to print debugging stmts */);
  }
}
