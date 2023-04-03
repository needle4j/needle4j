package org.needle4j.junit.builder;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.junit.DatabaseRule;
import org.needle4j.junit.NeedleRule;
import org.needle4j.junit.NeedleRuleBuilder;
import org.testng.Assert;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class NeedleBuilderWithOuterRulesTest {

  @Rule
  public NeedleRule rule = new NeedleRuleBuilder().withOuter(new DatabaseRule()).build();

  @Inject
  private EntityManager entityManager;

  @Test
  public void testDatabaseOuterRule() throws Exception {
    Assert.assertSame(new DatabaseRule().getEntityManager(), entityManager);
  }
}
