package com.games.nim;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NimActionTest {

  @Test
  public void testEquality() {
    NimAction action1 = new NimAction(1, 5);
    NimAction action2 = new NimAction(1, 5);
    assertThat(action1, equalTo(action2));
  }

  @Test
  public void testDifferentPileInequality() {
    NimAction action1 = new NimAction(1, 5);
    NimAction action2 = new NimAction(2, 5);
    assertThat(action1, not(equalTo(action2)));
  }

  @Test
  public void testDifferentNumberOfTokensInequality() {
    NimAction action1 = new NimAction(1, 6);
    NimAction action2 = new NimAction(1, 5);
    assertThat(action1, not(equalTo(action2)));
  }
}
