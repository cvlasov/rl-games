package com.games.findepsilon;

import com.games.general.Action;

/**
 * Action taken by a player in a game of Find Epsilon.
 * <p>
 * An action represents a choice of the value of epsilon to use for a Monte
 * Carlo agent playing Tic-Tac-Toe against a random agent.
 */
public class FindEpsilonAction implements Action {

  /** Value of epsilon. */
  public final double epsilon;

  public FindEpsilonAction(double e) {
    this.epsilon = e;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!FindEpsilonAction.class.isAssignableFrom(o.getClass())) {
      return false;
    }
    final FindEpsilonAction other = (FindEpsilonAction) o;
    return this.epsilon == other.epsilon;
  }

  @Override
  public int hashCode() {
    long bits = Double.doubleToLongBits(epsilon);
    return (int) (bits ^ (bits >>> 32));
  }
  
  @Override
  public void print() {
    System.out.print("Epsilon = " + epsilon);
  }
}
