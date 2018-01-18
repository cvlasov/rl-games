package general;

/** */
public interface Agent {

  String getName();
  void initializeBeforeNewGame();
  Action chooseAction(State s);
  void giveReturn(int amount);
  void gameOver();
}
