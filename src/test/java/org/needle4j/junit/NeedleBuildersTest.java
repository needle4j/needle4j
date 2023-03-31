package org.needle4j.junit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.mock.MockitoProvider;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static org.needle4j.junit.NeedleBuilders.*;

public class NeedleBuildersTest {

  @Rule
  public TestRule rule = outerRule(needleTestRule(this).build()).around(databaseTestRule().build());

  @ObjectUnderTest()
  private Runnable runnable = new Runnable() {

    @Inject
    private Instance<Runnable> instance;

    @Override
    public void run() {
      instance.get();

    }
  };

  @Inject
  private MockitoProvider mockitoProvider;

  @Inject
  private EntityManager entityManager;

  @Test
  public void testRuleChain() throws Exception {
    Assert.assertNotNull(mockitoProvider);
    Assert.assertNotNull(runnable);
    Assert.assertNotNull(entityManager);
  }

}
