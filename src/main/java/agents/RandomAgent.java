package agents;

import general.Action;
import general.Agent;
import general.State;

import java.util.List;

public class RandomAgent implements Agent {

  @Override
  public String getName() {
    return "Random";
  }

  @Override
  public void initializeBeforeNewGame() {
    //System.out.println("Random agent: initializing before new game");
    // do nothing
  }

  @Override
  public Action chooseAction(State s) {
    //System.out.println("Random agent: choosing action");
    List<Action> actions = s.getActions();
    int randomIndex = (int) (Math.random() * actions.size());
    return actions.get(randomIndex);
  }

  @Override
  public void giveReturn(int amount) {
    //System.out.println("Random agent: getting return");
    // do nothing
  }

  @Override
  public void gameOver() {
    //System.out.println("Random agent: GAME OVER");
    // do nothing
  }
}
