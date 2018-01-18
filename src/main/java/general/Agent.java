package general;

/** Interface for an agent that plays a game. */
public interface Agent {
  String getName();
  void initializeBeforeNewGame();
  Action chooseAction(State s);
  void receiveReturn(int amount);
  void gameOver();
}
