package com.games.chungtoi;

import static com.games.chungtoi.ChungToiHelper.TokenType;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ChungToiPutActionTest {

  @Test
  public void testEquality() {
    ChungToiPutAction action1 = new ChungToiPutAction(TokenType.X_NORMAL, 0);
    ChungToiPutAction action2 = new ChungToiPutAction(TokenType.X_NORMAL, 0);
    assertThat(action1, equalTo(action2));
  }

  @Test
  public void testDifferentTokenTypeInequality() {
    ChungToiPutAction action1 = new ChungToiPutAction(TokenType.X_NORMAL, 0);
    ChungToiPutAction action2 = new ChungToiPutAction(TokenType.O_DIAGONAL, 0);
    assertThat(action1, not(equalTo(action2)));
  }

  @Test
  public void testDifferentIndexInequality() {
    ChungToiPutAction action1 = new ChungToiPutAction(TokenType.X_NORMAL, 0);
    ChungToiPutAction action2 = new ChungToiPutAction(TokenType.X_NORMAL, 1);
    assertThat(action1, not(equalTo(action2)));
  }
}
