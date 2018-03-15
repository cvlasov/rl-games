package com.games.agents;

import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import com.games.general.Action;
import com.games.general.State;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

@RunWith(Parameterized.class)
public class MonteCarloAgentPolicyEvaluationTest {

  private final double EPSILON = 0.1;

  private State state;
  private Action action;
  private MonteCarloAgent mc;

  @Parameters
  public static Collection<Object[]> returns() {
     return Arrays.asList(new Object[][] {
     // { oldAverage,      oldCount,        newReturn,       expectedNewAverage, expectedNewCount }
        { new Double(1.0), new Integer(5),  new Double( 1.0), new Double( 1.0),  new Integer(6) },
        { new Double(4.0), new Integer(1),  new Double( 6.0), new Double( 5.0),  new Integer(2) },
        { new Double(0.0), new Integer(0),  new Double(-1.0), new Double(-1.0),  new Integer(1) }
     });
  }

  @Parameter(0)
  public Double oldAverage;

  @Parameter(1)
  public Integer oldCount;

  @Parameter(2)
  public Double newReturn;

  @Parameter(3)
  public Double expectedNewAverage;

  @Parameter(4)
  public Integer expectedNewCount;

  @Before
  public void setUp() {
    state = mock(State.class);
    action = mock(Action.class);

    Map<Action, Double> newActionReturnsMap = new HashMap<>();
    newActionReturnsMap.put(action, newReturn);

    Map<State, Map<Action, Double>> episodeStates = new HashMap<>();
    episodeStates.put(state, newActionReturnsMap);

    Map<Action, Double> averageReturnsMap = new HashMap<>();
    averageReturnsMap.put(action, oldAverage);

    Map<State, Map<Action, Double>> actionValueFunction = new HashMap<>();
    actionValueFunction.put(state, averageReturnsMap);

    Map<Action, Integer> actionCountsMap = new HashMap<>();
    actionCountsMap.put(action, oldCount);

    Map<State, Map<Action, Integer>> stateActionCounts = new HashMap<>();
    stateActionCounts.put(state, actionCountsMap);

    mc = new MonteCarloAgent(
        EPSILON, episodeStates, actionValueFunction, stateActionCounts);
    mc.policyEvaluation();
  }

  @Test
  public void testPolicyEvaluationUpdatesStateActionCounts() {
    assertThat(mc.getStateActionCounts(), hasKey(state));
    assertThat(mc.getStateActionCounts().get(state), hasKey(action));
    assertThat(mc.getStateActionCounts().get(state).get(action),
               equalTo(expectedNewCount));
  }

  @Test
  public void testPolicyEvaluationUpdatesActionValueFunction() {
    assertThat(mc.getActionValueFunction(), hasKey(state));
    assertThat(mc.getActionValueFunction().get(state), hasKey(action));
    assertThat(mc.getActionValueFunction().get(state).get(action),
               equalTo(expectedNewAverage));
  }
}
