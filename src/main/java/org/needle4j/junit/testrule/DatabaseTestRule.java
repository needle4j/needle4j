package org.needle4j.junit.testrule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.needle4j.configuration.NeedleConfiguration;
import org.needle4j.db.DatabaseTestcase;
import org.needle4j.db.operation.DBOperation;

public class DatabaseTestRule extends DatabaseTestcase implements TestRule {

  /**
   * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase()
   */
  public DatabaseTestRule() {
    super();
  }

  /**
   * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(DBOperation)
   */
  public DatabaseTestRule(final DBOperation dbOperation) {
    super(dbOperation);
  }

  /**
   * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(String,
   * DBOperation)
   */
  public DatabaseTestRule(final String persistenceUnitName, final DBOperation dbOperation) {
    super(persistenceUnitName, dbOperation);
  }

  /**
   * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(String)
   */
  public DatabaseTestRule(final String persistenceUnitName) {
    super(persistenceUnitName);
  }

  DatabaseTestRule(final NeedleConfiguration configuration) {
    super(configuration);
  }

  @Override
  public Statement apply(final Statement base, final Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          before();
          base.evaluate();
        } finally {
          after();
        }
      }
    };
  }

}
