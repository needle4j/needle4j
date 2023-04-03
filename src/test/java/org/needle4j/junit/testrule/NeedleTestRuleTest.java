package org.needle4j.junit.testrule;

import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;

import jakarta.inject.Inject;

import static org.junit.Assert.assertNotNull;

public class NeedleTestRuleTest {

  public static class DummyTarget {

    @Inject
    private Runnable runner;

    public void execute() {
      runner.run();
    }

  }

  @ObjectUnderTest
  private DummyTarget dummyTarget;

  @Inject
  private Runnable runnerMock;

  @Rule
  public final NeedleTestRule needle = new NeedleTestRule(this);

  @Test
  public void shouldCreateClassAndExecute() {
    assertNotNull(dummyTarget);
    assertNotNull(runnerMock);

    EasyMock.resetToStrict(runnerMock);
    runnerMock.run();

    EasyMock.replay(runnerMock);
    dummyTarget.execute();

    EasyMock.verify(runnerMock);

  }

}
