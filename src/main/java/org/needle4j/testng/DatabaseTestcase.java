package org.needle4j.testng;

import org.needle4j.db.operation.DBOperation;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @see org.needle4j.db.DatabaseTestcase
 */
public class DatabaseTestcase extends org.needle4j.db.DatabaseTestcase {

  /**
   * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase()
   */
  public DatabaseTestcase() {
    super();
  }

  /**
   * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(DBOperation)
   */
  public DatabaseTestcase(final DBOperation dbOperation) {
    super(dbOperation);
  }

  /**
   * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(String,
   * DBOperation)
   */
  public DatabaseTestcase(final String persistenceUnitName, final DBOperation dbOperation) {
    super(persistenceUnitName, dbOperation);
  }

  /**
   * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(String)
   */
  public DatabaseTestcase(final String persistenceUnitName) {
    super(persistenceUnitName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @AfterMethod
  public void after() throws Exception {
    super.after();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @BeforeMethod
  public void before() throws Exception {
    super.before();
  }

}
