package org.needle4j.junit.testrule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DatabaseTestRuleTest {

  @Rule
  public DatabaseTestRule databaseTestRule = new DatabaseTestRule();

  @Test
  public void testEntityManager() {
    assertNotNull(databaseTestRule.getEntityManager());
  }
}
