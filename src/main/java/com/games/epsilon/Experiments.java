package com.games.epsilon;

import com.games.agents.MonteCarloAgent;
import com.games.agents.RandomAgent;
import com.games.general.Agent;
import com.games.tictactoe.TicTacToeGame;
import com.games.tictactoe.TicTacToeResults;

/**
 * Game of Find Epsilon, where there is one player and the game ends after one
 * move. The available actions are all possible values of epsilon and each
 * return is the win rate for using the chosen value of epsilon.
 */
final class Experiments {

  /**
   * Plays the given number of games of Tic-Tac-Toe between a Monte Carlo agent
   * (with the given value of epsilon) and a random agent.
   *
   * @param epsilon  the value of epsilon to use for the Monte Carlo agent
   * @param numGames the number of games of Tic-Tac-Toe to play
   * @return the results of playing all the games
   */
  static TicTacToeResults playTicTacToe(double epsilon, int numGames) {
    int[] wins = new int[3]; // index 0 is draw, 1 is agent1, 2 is agent2
    TicTacToeGame game;

    MonteCarloAgent monteCarloAgent = new MonteCarloAgent(epsilon);
    RandomAgent randomAgent = new RandomAgent();

    for (int i = 0 ; i < numGames ; i++) {
      game = new TicTacToeGame(monteCarloAgent, randomAgent);
      int winner = game.play();

      if (winner == -1) {
        System.out.println("ERROR");
      }

      wins[winner]++;
    }

    // Log value of epsilon to the console to keep track of how many epsilon
    // values have been tried so far
    System.out.println("epsilon = " + epsilon);

    return new TicTacToeResults(wins[1] /* wins   */,
                                wins[2] /* losses */,
                                wins[0] /* draws  */);
  }
}