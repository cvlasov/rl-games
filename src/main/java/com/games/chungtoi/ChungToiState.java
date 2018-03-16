package com.games.chungtoi;

import static com.games.chungtoi.ChungToiHelper.GRID_SIZE;
import static com.games.chungtoi.ChungToiHelper.TOKENS_PER_PLAYER;

import com.games.chungtoi.ChungToiHelper.TokenType;
import com.games.chungtoi.ChungToiHelper.Player;
import com.games.chungtoi.ChungToiHelper.Winner;
import com.games.general.Action;
import com.games.general.State;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** State of a game of Chung Toi. */
public class ChungToiState implements State {

  /**
   * Chung Toi grid, where the list indices represent the board as:
   *    0 | 1 | 2
   *   -----------
   *    3 | 4 | 5
   *   -----------
   *    6 | 7 | 8
   */
  private final List<TokenType> grid;

  /** List of possible actions to take from this state. */
  private List<Action> actions;

  private Player nextTurn;

  /**
   * Winning token type (or draw) if this is a terminal state, otherwise
   * {@link ChungToiHelper#Winner.GAME_NOT_OVER}. Null if
   * {@link #isTerminalState()} has not yet been called.
   */
  private Winner winner = null;


  // CONSTRUCTORS

  public ChungToiState() {
    grid = new ArrayList<>(Collections.nCopies(GRID_SIZE, TokenType.NONE));
    nextTurn = Player.X;
    computeActions();
    isTerminalState();  // ignore result
  }

  /** Creates a copy of the given state. */
  private ChungToiState(ChungToiState oldState) {
    grid = new ArrayList<>(oldState.grid);
    nextTurn = oldState.nextTurn;
    computeActions();
    isTerminalState();  // ignore result
  }

  /**
   * Creates the state that results from applying the given "move" action at
   * the given state.
   */
  private ChungToiState(ChungToiState oldState, ChungToiMoveAction action) {
    grid = new ArrayList<>(oldState.grid);
    TokenType type = oldState.grid.get(action.startIndex);
    grid.set(action.startIndex, TokenType.NONE);

    if (action.rotateToken) {
      switch(type) {
        case X_NORMAL:   grid.set(action.endIndex, TokenType.X_DIAGONAL); break;
        case X_DIAGONAL: grid.set(action.endIndex, TokenType.X_NORMAL); break;
        case O_NORMAL:   grid.set(action.endIndex, TokenType.O_DIAGONAL); break;
        case O_DIAGONAL: grid.set(action.endIndex, TokenType.O_NORMAL); break;
        case NONE:       break;  // should not reach here
      }
    } else {
      grid.set(action.endIndex, type);
    }

    switch (oldState.nextTurn) {
      case X: nextTurn = Player.O; break;
      case O: nextTurn = Player.X; break;
    }

    computeActions();
    isTerminalState();  // ignore result
  }

  /**
   * Creates the state that results from applying the given "pass" action at
   * the given state.
   */
  private ChungToiState(ChungToiState oldState, ChungToiPassAction action) {
    grid = new ArrayList<>(oldState.grid);
    switch (oldState.nextTurn) {
      case X: nextTurn = Player.O; break;
      case O: nextTurn = Player.X; break;
    }
    computeActions();
    isTerminalState();  // ignore result
  }

  /**
   * Creates the state that results from applying the given "put" action at
   * the given state.
   */
  private ChungToiState(ChungToiState oldState, ChungToiPutAction action) {
    grid = new ArrayList<>(oldState.grid);
    grid.set(action.index, action.tokenType);
    switch (oldState.nextTurn) {
      case X: nextTurn = Player.O; break;
      case O: nextTurn = Player.X; break;
    }
    computeActions();
    isTerminalState();  // ignore result
  }

  @VisibleForTesting
  ChungToiState(List<TokenType> g, Player next) {
    grid = new ArrayList<>(g);
    nextTurn = next;
    computeActions();
    isTerminalState();
  }


  // OVERWRITTEN METHODS FROM OBJECT

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!ChungToiState.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    final ChungToiState other = (ChungToiState) o;

    for (int i = 0 ; i < GRID_SIZE ; i++) {
      if (this.grid.get(i) != other.grid.get(i)) {
        return false;
      }
    }

    return this.nextTurn == other.nextTurn;
  }

  @Override
  public int hashCode() {
    int hash = 3 * grid.get(0).hashCode();
    hash +=    5 * grid.get(1).hashCode();
    hash +=    7 * grid.get(2).hashCode();
    hash +=   11 * grid.get(3).hashCode();
    hash +=   13 * grid.get(4).hashCode();
    hash +=   17 * grid.get(5).hashCode();
    hash +=   19 * grid.get(6).hashCode();
    hash +=   23 * grid.get(7).hashCode();
    hash +=   29 * grid.get(8).hashCode();
    return hash;
  }


  // IMPLEMENTATIONS OF STATE INTERFACE METHODS

  @Override
  public State applyAction(Action action) {
    if (ChungToiMoveAction.class.isAssignableFrom(action.getClass())){
      return new ChungToiState(this, (ChungToiMoveAction) action);

    } else if (ChungToiPassAction.class.isAssignableFrom(action.getClass())){
      return new ChungToiState(this, (ChungToiPassAction) action);

    } else if (ChungToiPutAction.class.isAssignableFrom(action.getClass())){
      return new ChungToiState(this, (ChungToiPutAction) action);

    } else {
      return null;
    }
  }

  @Override
  public State copy() {
    return new ChungToiState(this);
  }

  @Override
  public List<Action> getActions() {
    return actions;
  }

  @Override
  public boolean isTerminalState() {
    // Only check once, so use previously computed result if called again
    if (winner != null) {
      switch (winner) {
        case X:             // fall through
        case O:             // fall through
        case DRAW:          return true;
        case GAME_NOT_OVER: return false;
      }
    }

    if (grid.get(0) != TokenType.NONE) {
      if (ChungToiHelper.threeInARow(grid.get(0), grid.get(1), grid.get(2))
          || ChungToiHelper.threeInARow(grid.get(0), grid.get(4), grid.get(8))
          || ChungToiHelper.threeInARow(grid.get(0), grid.get(3), grid.get(6))) {
        switch (grid.get(0)) {
          case X_NORMAL:   // fall through
          case X_DIAGONAL: winner = Winner.X; return true;
          case O_NORMAL:   // fall through
          case O_DIAGONAL: winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }

    if (grid.get(4) != TokenType.NONE) {
      if (ChungToiHelper.threeInARow(grid.get(3), grid.get(4), grid.get(5))
          || ChungToiHelper.threeInARow(grid.get(1), grid.get(4), grid.get(7))
          || ChungToiHelper.threeInARow(grid.get(2), grid.get(4), grid.get(6))) {
        switch (grid.get(4)) {
          case X_NORMAL:   // fall through
          case X_DIAGONAL: winner = Winner.X; return true;
          case O_NORMAL:   // fall through
          case O_DIAGONAL: winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }

    if (grid.get(8) != TokenType.NONE) {
      if (ChungToiHelper.threeInARow(grid.get(6), grid.get(7), grid.get(8))
          || ChungToiHelper.threeInARow(grid.get(2), grid.get(5), grid.get(8))) {
        switch (grid.get(8)) {
          case X_NORMAL:   // fall through
          case X_DIAGONAL: winner = Winner.X; return true;
          case O_NORMAL:   // fall through
          case O_DIAGONAL: winner = Winner.O; return true;
          case NONE: break;  // will not reach this case
        }
      }
    }

    winner = Winner.GAME_NOT_OVER;
    return false;
  }

  @Override
  public void print() {
    ChungToiHelper.print(this.grid);
  }

  /**
   * Returns the winner of the game, if any.
   * <p>
   * Can only be called after {@link #isTerminalState()}.
   *
   * @return winner of the game, if any
   */
  Winner getWinner() {
    return winner;
  }

  // PRIVATE HELPER METHODS

  private void computeActions() {
    // Actions are only computed once
    if (actions != null) return;

    actions = new ArrayList<>();
    actions.add(ChungToiPassAction.getInstance());  // always offer this

    // Check if next player has put down all three pieces
    int count = 0; // X's turn if diff = 0, O's turn if diff = 1

    for (TokenType t : grid) {
      switch (t) {
        case X_NORMAL:    // fall through
        case X_DIAGONAL:  if (nextTurn == Player.X) count += 1; break;
        case O_NORMAL:    // fall through
        case O_DIAGONAL:  if (nextTurn == Player.O) count += 1; break;
        case NONE: break;
      }
    }

    // If not all pieces have been put down, only offer "put" actions
    if (count < TOKENS_PER_PLAYER) {
      for (int i = 0; i < GRID_SIZE ; i++) {
        if (grid.get(i) == TokenType.NONE) {
          switch (nextTurn) {
            case X:
              actions.add(new ChungToiPutAction(TokenType.X_NORMAL, i));
              actions.add(new ChungToiPutAction(TokenType.X_DIAGONAL, i));
              break;
            case O:
              actions.add(new ChungToiPutAction(TokenType.O_NORMAL, i));
              actions.add(new ChungToiPutAction(TokenType.O_DIAGONAL, i));
              break;
          }
        }
      }

      return;
    }

    // All pieces have been put down, so check which pieces can be moved/rotated
    for (int i = 0; i < GRID_SIZE ; i++) {
      switch (grid.get(i)) {
        case X_NORMAL:    // fall through
        case X_DIAGONAL:  if (nextTurn == Player.X) getActionsForIndex(i); break;
        case O_NORMAL:    // fall through
        case O_DIAGONAL:  if (nextTurn == Player.O) getActionsForIndex(i); break;
        case NONE: break;
      }
    }
  }

  private void getActionsForIndex(int i) {
    actions.add(new ChungToiMoveAction(i, i, true));
    // don't add the equivalent action with "false" because this means doing
    // nothing, which is the purpose of the "pass" action

    TokenType tokenType = grid.get(i);
    List<Integer> endIndices = new ArrayList<>();

    switch (i) {
      case 0 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(1) == TokenType.NONE) {
            endIndices.add(1);
            if (grid.get(2) == TokenType.NONE) endIndices.add(2);
          }
          // Vertically
          if (grid.get(3) == TokenType.NONE) {
            endIndices.add(3);
            if (grid.get(6) == TokenType.NONE) endIndices.add(6);
          }
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(4) == TokenType.NONE) {
            endIndices.add(4);
            if (grid.get(8) == TokenType.NONE) endIndices.add(8);
          }
        }
        break;

      case 1 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(0) == TokenType.NONE) endIndices.add(0);
          if (grid.get(2) == TokenType.NONE) endIndices.add(2);
          // Vertically
          if (grid.get(4) == TokenType.NONE) {
            endIndices.add(4);
            if (grid.get(7) == TokenType.NONE) endIndices.add(7);
          }
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(3) == TokenType.NONE) endIndices.add(3);
          if (grid.get(5) == TokenType.NONE) endIndices.add(5);
        }
        break;

      case 2 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(1) == TokenType.NONE) {
            endIndices.add(1);
            if (grid.get(0) == TokenType.NONE) endIndices.add(0);
          }
          // Vertically
          if (grid.get(5) == TokenType.NONE) {
            endIndices.add(5);
            if (grid.get(8) == TokenType.NONE) endIndices.add(8);
          }
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(4) == TokenType.NONE) {
            endIndices.add(4);
            if (grid.get(6) == TokenType.NONE) endIndices.add(6);
          }
        }
        break;

      case 3 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(4) == TokenType.NONE) {
            endIndices.add(4);
            if (grid.get(5) == TokenType.NONE) endIndices.add(5);
          }
          // Vertically
          if (grid.get(0) == TokenType.NONE) endIndices.add(0);
          if (grid.get(6) == TokenType.NONE) endIndices.add(6);
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(1) == TokenType.NONE) endIndices.add(1);
          if (grid.get(7) == TokenType.NONE) endIndices.add(7);
        }
        break;

      case 4 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(3) == TokenType.NONE) endIndices.add(3);
          if (grid.get(5) == TokenType.NONE) endIndices.add(5);
          // Vertically
          if (grid.get(1) == TokenType.NONE) endIndices.add(1);
          if (grid.get(7) == TokenType.NONE) endIndices.add(7);
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(0) == TokenType.NONE) endIndices.add(0);
          if (grid.get(2) == TokenType.NONE) endIndices.add(2);
          if (grid.get(6) == TokenType.NONE) endIndices.add(6);
          if (grid.get(8) == TokenType.NONE) endIndices.add(8);
        }
        break;

      case 5 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(4) == TokenType.NONE) {
            endIndices.add(4);
            if (grid.get(3) == TokenType.NONE) endIndices.add(3);
          }
          // Vertically
          if (grid.get(2) == TokenType.NONE) endIndices.add(2);
          if (grid.get(8) == TokenType.NONE) endIndices.add(8);
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(1) == TokenType.NONE) endIndices.add(1);
          if (grid.get(7) == TokenType.NONE) endIndices.add(7);
        }
        break;

      case 6 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(7) == TokenType.NONE) {
            endIndices.add(7);
            if (grid.get(8) == TokenType.NONE) endIndices.add(8);
          }
          // Vertically
          if (grid.get(3) == TokenType.NONE) {
            endIndices.add(3);
            if (grid.get(0) == TokenType.NONE) endIndices.add(0);
          }
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(4) == TokenType.NONE) {
            endIndices.add(4);
            if (grid.get(2) == TokenType.NONE) endIndices.add(2);
          }
        }
        break;

      case 7 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(6) == TokenType.NONE) endIndices.add(6);
          if (grid.get(8) == TokenType.NONE) endIndices.add(8);
          // Vertically
          if (grid.get(4) == TokenType.NONE) {
            endIndices.add(4);
            if (grid.get(1) == TokenType.NONE) endIndices.add(1);
          }
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(3) == TokenType.NONE) endIndices.add(3);
          if (grid.get(5) == TokenType.NONE) endIndices.add(5);
        }
        break;

      case 8 :
        // Can move horizontally/vertically
        if (tokenType == TokenType.X_NORMAL || tokenType == TokenType.O_NORMAL) {
          // Horizontally
          if (grid.get(7) == TokenType.NONE) {
            endIndices.add(7);
            if (grid.get(6) == TokenType.NONE) endIndices.add(6);
          }
          // Vertically
          if (grid.get(5) == TokenType.NONE) {
            endIndices.add(5);
            if (grid.get(2) == TokenType.NONE) endIndices.add(2);
          }
        }
        // Can move diagonally
        if (tokenType == TokenType.X_DIAGONAL || tokenType == TokenType.O_DIAGONAL) {
          if (grid.get(4) == TokenType.NONE) {
            endIndices.add(4);
            if (grid.get(0) == TokenType.NONE) endIndices.add(0);
          }
        }
        break;

      default:
        break;
    }

    for (int end : endIndices) {
      actions.add(new ChungToiMoveAction(i, end, false));
      actions.add(new ChungToiMoveAction(i, end, true));
    }
  }
}
