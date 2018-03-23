package com.games.general;

import java.util.List;

/** Interface for the state of a game. */
public interface State {
  State applyAction(Action a);
  List<Action> getActions();
  boolean isTerminalState();
  void print();
}
