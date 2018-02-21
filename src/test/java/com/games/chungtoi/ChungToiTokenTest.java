package com.games.chungtoi;

import static com.games.chungtoi.ChungToiHelper.TokenOrientation;
import static com.games.chungtoi.ChungToiHelper.TokenType;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ChungToiTokenTest {

  @Test
  public void testTokenEquality() {
    ChungToiToken token1 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    ChungToiToken token2 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    assertThat(token1, equalTo(token2));
  }

  @Test
  public void testDifferentTypeInequality() {
    ChungToiToken token1 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    ChungToiToken token2 = new ChungToiToken(TokenType.O, TokenOrientation.DIAGONAL);
    assertThat(token1, not(equalTo(token2)));
  }

  @Test
  public void testDifferentOrientationInequality() {
    ChungToiToken token1 = new ChungToiToken(TokenType.X, TokenOrientation.DIAGONAL);
    ChungToiToken token2 = new ChungToiToken(TokenType.X, TokenOrientation.CARDINAL);
    assertThat(token1, not(equalTo(token2)));
  }
}
