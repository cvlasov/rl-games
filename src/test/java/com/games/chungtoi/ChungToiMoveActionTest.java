package com.games.chungtoi;

import static com.games.chungtoi.ChungToiHelper.TokenType;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ChungToiMoveActionTest {

  @Test
  public void testEquality() {
    ChungToiMoveAction action1 = new ChungToiMoveAction(0, 1, true);
    ChungToiMoveAction action2 = new ChungToiMoveAction(0, 1, true);
    assertThat(action1, equalTo(action2));
  }

  @Test
  public void testDifferentStartIndexInequality() {
    ChungToiMoveAction action1 = new ChungToiMoveAction(0, 1, true);
    ChungToiMoveAction action2 = new ChungToiMoveAction(2, 1, true);
    assertThat(action1, not(equalTo(action2)));
  }

  @Test
  public void testDifferentEndIndexInequality() {
    ChungToiMoveAction action1 = new ChungToiMoveAction(0, 1, true);
    ChungToiMoveAction action2 = new ChungToiMoveAction(0, 2, true);
    assertThat(action1, not(equalTo(action2)));
  }

  @Test
  public void testDifferentRotateTokenInequality() {
    ChungToiMoveAction action1 = new ChungToiMoveAction(0, 1, true);
    ChungToiMoveAction action2 = new ChungToiMoveAction(0, 1, false);
    assertThat(action1, not(equalTo(action2)));
  }
}
