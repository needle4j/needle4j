package org.needle4j.junit.testrule;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.needle4j.junit.NeedleBuilders;

public class DatabaseTestRuleBuilderTest {

  @Test
  public void testBuilder() throws Throwable {
    DatabaseTestRule build = NeedleBuilders.databaseTestRule().build();

    Statement statement = new Statement() {

      @Override
      public void evaluate() throws Throwable {
      }
    };
    build.apply(statement, null);

    statement.evaluate();

    Assert.assertNotNull(build.getEntityManager());
  }
}
