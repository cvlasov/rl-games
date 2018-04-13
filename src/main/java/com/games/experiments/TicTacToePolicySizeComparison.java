package com.games.experiments;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.general.State;
import com.games.tictactoe.TicTacToeGameWithLimitedActions;
import com.games.tictactoe.TicTacToeGameWithSymmetricEquality;
import com.games.tictactoe.TicTacToeNormalGame;
import java.util.Set;

import java.io.IOException;

public final class TicTacToePolicySizeComparison {

  public static void main(String[] args) throws IOException {
    RandomAgent randAgent = new RandomAgent();

    int[] winsN = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    MonteCarloAgent mcAgentN = new MonteCarloAgent(0.9, false);
    TicTacToeNormalGame gameN;

    for (int gamesSoFar = 1 ; gamesSoFar <= 100000 ; gamesSoFar++) {
      gameN = new TicTacToeNormalGame(mcAgentN, randAgent);
      int winner = gameN.play();
      if (winner == -1) System.out.println("ERROR");
      winsN[winner]++;
    }

    int[] winsLA = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    MonteCarloAgent mcAgentLA = new MonteCarloAgent(0.9, false);
    TicTacToeGameWithLimitedActions gameLA;

    for (int gamesSoFar = 1 ; gamesSoFar <= 100000 ; gamesSoFar++) {
      gameLA = new TicTacToeGameWithLimitedActions(mcAgentLA, randAgent);
      int winner = gameLA.play();
      if (winner == -1) System.out.println("ERROR");
      winsLA[winner]++;
    }

    int[] winsSE = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    MonteCarloAgent mcAgentSE = new MonteCarloAgent(0.9, false);
    TicTacToeGameWithSymmetricEquality gameSE;

    for (int gamesSoFar = 1 ; gamesSoFar <= 100000 ; gamesSoFar++) {
      gameSE = new TicTacToeGameWithSymmetricEquality(mcAgentSE, randAgent);
      int winner = gameSE.play();
      if (winner == -1) System.out.println("ERROR");
      winsSE[winner]++;
    }

    Set<State> Nstates = mcAgentN.getPolicy().keySet();
    Set<State> LAstates = mcAgentLA.getPolicy().keySet();
    Set<State> SEstates = mcAgentSE.getPolicy().keySet();

    System.out.println("Normal states: " + Nstates.size());
    System.out.println("Limited Actions states: " + LAstates.size());
    System.out.println("Symmetric Equality states: " + SEstates.size());

    System.out.println("Normal states contains all Limited Actions states? "
                       + Nstates.containsAll(LAstates));

    System.out.println("Normal states contains all Symmetric Equality states? "
                       + Nstates.containsAll(SEstates));

    LAstates.retainAll(SEstates);
    System.out.println("Size of intersection of LA and SE states: "
                       + LAstates.size());

    LAstates = mcAgentLA.getPolicy().keySet();
    LAstates.retainAll(Nstates);
    System.out.println("Size of intersection of LA and N states: "
                       + LAstates.size());

    LAstates = mcAgentLA.getPolicy().keySet();
    LAstates.removeAll(Nstates);
    System.out.println("LAstates that are NOT in Nstates: "
                       + LAstates.size());

    // Print all states in LAstates but not in Nstates
    for (State s : LAstates) {
      s.print();
      System.out.println();
    }
  }
}
