package com.games.chungtoi;

import static com.games.chungtoi.ChungToiHelper.TokenOrientation;
import static com.games.chungtoi.ChungToiHelper.TokenType;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ChungToiPutActionTest {

  @Test
  public void testEquality() {
    ChungToiToken token1 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    ChungToiToken token2 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    ChungToiPutAction action1 = new ChungToiPutAction(token1, 0);
    ChungToiPutAction action2 = new ChungToiPutAction(token2, 0);
    assertThat(action1, equalTo(action2));
  }

  @Test
  public void testDifferentTokenInequality() {
    ChungToiToken token1 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    ChungToiToken token2 = new ChungToiToken(TokenType.O, TokenOrientation.DIAGONAL);
    ChungToiPutAction action1 = new ChungToiPutAction(token1, 0);
    ChungToiPutAction action2 = new ChungToiPutAction(token2, 0);
    assertThat(action1, not(equalTo(action2)));
  }

  @Test
  public void testDifferentIndexInequality() {
    ChungToiToken token1 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    ChungToiToken token2 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    ChungToiPutAction action1 = new ChungToiPutAction(token1, 0);
    ChungToiPutAction action2 = new ChungToiPutAction(token2, 1);
    assertThat(action1, not(equalTo(action2)));
  }
}
