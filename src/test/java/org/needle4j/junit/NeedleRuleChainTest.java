package org.needle4j.junit;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.Mock;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static org.junit.Assert.assertNotNull;

public class NeedleRuleChainTest {

  @Rule
  public NeedleRule needleRule = new NeedleRule().withOuter(new DatabaseRule());

  @Inject
  private EntityManager entityManager;

  @Mock
  private Runnable runnableMock;

  @Test
  public void testDtabaseRuleInjection() throws Exception {
    assertNotNull(entityManager);
  }

  @Test
  public void testMockCreation() throws Exception {
    assertNotNull(runnableMock);
  }

}
