package general;

import java.util.List;

public interface State {

  List<Action> getActions();
  State applyAction(Action a);
  boolean checkIfTerminalState();
  void print();
  State copy();
}
