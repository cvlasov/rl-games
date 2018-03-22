package com.games.general;

import java.util.List;

/** Interface for the state of a game. */
public interface State {
  List<Action> getActions();
  State applyAction(Action a);
  boolean isTerminalState();
  void print();
}
