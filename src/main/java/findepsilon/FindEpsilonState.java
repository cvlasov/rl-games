package findepsilon;

import general.Action;
import general.State;

import java.util.ArrayList;
import java.util.List;

/**
 * State of a game of Find Epsilon.
 * <p>
 * Singleton since there is only one state in this game.
 */
public class FindEpsilonState implements State {

  private static FindEpsilonState state = null;

  /** List of possible actions to take from this state. */
  private final List<Action> actions = new ArrayList<>();

  /** Creates a state. */
  protected FindEpsilonState() {
    computeActions();
  }

  /** Returns the sole instance of FindEpsilonState. */
  public static FindEpsilonState getInstance() {
    if (state == null) {
      state = new FindEpsilonState();
    }
    
    return state;
  }
  
  @Override
  public List<Action> getActions() {
    return actions;
  }
  
  @Override
  public State applyAction(Action a) {
    return state; // state never changes
  }
  
  @Override
  public State copy() {
    return state;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    
    return FindEpsilonState.class.isAssignableFrom(o.getClass());
  }
  
  @Override
  public boolean checkIfTerminalState() {
    return false;
  }
  
  @Override
  public int hashCode() {
    return 13;
  }
  
  @Override
  public void print() {
    System.out.println("[singleton FindEpsilonState]");
  }
  
  private void computeActions() {
    for (double e = FindEpsilonGame.granularityOfEpsilon ;
         e < 1.0 ;
         e += FindEpsilonGame.granularityOfEpsilon) {
      actions.add(new FindEpsilonAction(e));
    }
  }
}
