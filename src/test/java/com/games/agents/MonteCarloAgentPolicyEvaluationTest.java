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
import org.junit.Test;

@RunWith(Parameterized.class)
public class MonteCarloAgentPolicyEvaluationTest {

  @Parameters
  public static Collection<Object[]> returns() {
     return Arrays.asList(new Object[][] {
        { new Double[] { 1.0 },           1.0 },
        { new Double[] { 2.0, 6.0, 4.0 }, 4.0 },
     });
  }

  @Parameter(0)
  public Double[] returns;

  @Parameter(1)
  public Double expectedAverage;

  @Test
  public void testPolicyEvaluation() {
    State state = mock(State.class);

    Action action = mock(Action.class);
    List<Action> actions = new ArrayList<>(); actions.add(action);

    List<Double> returnsList = new ArrayList<Double>(Arrays.asList(returns));

    Map<Action, List<Double>> returnsMap = new HashMap<>();
    returnsMap.put(action, returnsList);

    Map<State, List<Action>> episodeStates = new HashMap<>();
    episodeStates.put(state, actions);

    Map<State, Map<Action, List<Double>>> overallReturns = new HashMap<>();
    overallReturns.put(state, returnsMap);

    MonteCarloAgent mc = new MonteCarloAgent(0.1, episodeStates, overallReturns);
    mc.policyEvaluation();

    assertThat(mc.getActionValueFunction(), hasKey(state));
    assertThat(mc.getActionValueFunction().get(state), hasKey(action));
    assertThat(mc.getActionValueFunction().get(state).get(action),
               equalTo(expectedAverage));
  }
}
