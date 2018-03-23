package com.games.general;

import java.util.List;

/** Interface for the state of a game. */
public interface State {

  /**
   * Returns the state that results from applying the given action to this
   * state.
   *
   * @param a action to apply to this state
   * @return  state that results from applying the given action to this state
   */
  State applyAction(Action a);

  /**
   * Returns the list of all actions that can be taken from this state.
   *
   * @return list of available actions
   */
  List<Action> getActions();

  /**
   * Returns whether or not this is an end game state.
   *
   * @return whether or not this is an end game state
   */
  boolean isTerminalState();

  /** Prints this state to the standard output stream. */
  void print();
}
