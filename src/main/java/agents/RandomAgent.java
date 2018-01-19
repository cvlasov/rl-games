package agents;

import general.Action;
import general.Agent;
import general.State;

import java.util.List;

/** Game-playing agent that uses a random strategy to choose actions. */
public class RandomAgent implements Agent {

  @Override
  public String getName() {
    return "Random";
  }

  @Override
  public void initializeBeforeNewGame() {
    // do nothing
  }

  @Override
  public Action chooseAction(State s) {
    List<Action> actions = s.getActions();
    int randomIndex = (int) (Math.random() * actions.size());
    return actions.get(randomIndex);
  }

  @Override
  public void receiveReturn(double amount) {
    // do nothing
  }

  @Override
  public void gameOver() {
    // do nothing
  }
}
