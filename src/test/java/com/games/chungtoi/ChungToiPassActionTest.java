package com.games.chungtoi;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChungToiPassActionTest {

  @Test
  public void testSingleton() {
    ChungToiPassAction action1 = ChungToiPassAction.getInstance();
    ChungToiPassAction action2 = ChungToiPassAction.getInstance();
    assertTrue(action1 == action2); // reference equality check
  }
}
