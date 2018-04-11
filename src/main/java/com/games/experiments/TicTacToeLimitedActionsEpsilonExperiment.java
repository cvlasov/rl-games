package com.games.experiments;

import static com.games.experiments.ExperimentHelper.GameType;
import static com.games.experiments.ExperimentHelper.saveEpsilonResultsInCSV;

import java.io.IOException;

/**
 * Main class for making agents play limited actions Tic-Tac-Toe against each
 * other multiple times while varying the learning parameter to determine the
 * best parameter values.
 */
public final class TicTacToeLimitedActionsEpsilonExperiment {

  public static void main(String[] args) throws IOException {
    saveEpsilonResultsInCSV(GameType.TIC_TAC_TOE_LIMITED_ACTIONS,
                            0.0,       /* epsilon start */
                            0.1,       /* epsilon end */
                            0.01,      /* epsilon precision */
                            1000*1000, /* number of games */
                            false      /* whether to print debugging stmts */);
  }
}
