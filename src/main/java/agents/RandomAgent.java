package agents;

import general.Action;
import general.Agent;
import general.State;

import java.util.List;

public class RandomAgent implements Agent {

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
  public void giveReturn(int amount) {
    // do nothing
  }

  @Override
  public void gameOver() {
    // do nothing
  }
}
