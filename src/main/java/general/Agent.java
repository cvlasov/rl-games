package general;

/** */
public interface Agent {

  void initializeBeforeNewGame();
  Action chooseAction(State s);
  void giveReturn(int amount);
  void gameOver();
}
