package com.games.experiments;

import static com.games.experiments.ExperimentHelper.GameType;
import static com.games.experiments.ExperimentHelper.saveEpsilonTestResultsInCSV;

import java.io.IOException;

/**
 * Main class for making agents play symmetric equality Tic-Tac-Toe against each
 * other multiple times while varying the learning parameter to determine the
 * best parameter values.
 */
public final class TicTacToeSymmetricEqualityEpsilonExperiment {

  public static void main(String[] args) throws IOException {
    saveEpsilonTestResultsInCSV(GameType.TIC_TAC_TOE_SYMMETRIC_EQUALITY,
                                0.0,       /* epsilon start */
                                1.0,       /* epsilon end */
                                0.01,      /* epsilon precision */
                                10*1000,   /* number of training games */
                                100*1000,  /* number of test games */
                                false      /* whether to print debugging stmts */);
  }
}
