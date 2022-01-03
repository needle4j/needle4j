package org.needle4j.db.operation;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ExecuteScriptOperationTest {

  private static final JdbcConfiguration HSQL_DB_CONFIGURATION = new JdbcConfiguration(
      "jdbc:hsqldb:mem:ExecuteScriptOperationTestDB", "org.hsqldb.jdbcDriver", "sa", "");

  private ExecuteScriptOperation executeScriptOperation = new ExecuteScriptOperation(HSQL_DB_CONFIGURATION);

  @Test
  public void testSetUpOperation() throws Exception {
    executeScriptOperation.setUpOperation();

    List<String> tableNames = executeScriptOperation.getTableNames(executeScriptOperation.getConnection());

    Assert.assertFalse(tableNames.isEmpty());

    executeScriptOperation.closeConnection();
  }

  @Test
  public void testTearDownOperation() throws Exception {
    executeScriptOperation.tearDownOperation();

    List<String> tableNames = executeScriptOperation.getTableNames(executeScriptOperation.getConnection());
    Assert.assertTrue(tableNames.isEmpty());
    executeScriptOperation.closeConnection();
  }

}
