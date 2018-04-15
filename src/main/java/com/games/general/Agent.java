package com.games.general;

/** Interface for an agent that plays a game. */
public interface Agent {
  String getName();
  void initializeBeforeNewGame();
  Action chooseAction(State s);
  Action chooseActionES(State s, Action a);
  void receiveReturn(double amount);
  void gameOver();
}
