package org.needle4j.db.operation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AbstractDBOperationTest {
  private static final JdbcConfiguration HSQL_DB_CONFIGURATION = new JdbcConfiguration(
      "jdbc:hsqldb:mem:AbstractDBOperationTestDB", "org.hsqldb.jdbcDriver", "sa", "");

  private final AbstractDBOperation dbOperation = new AbstractDBOperation(HSQL_DB_CONFIGURATION) {
    @Override
    public void tearDownOperation() {
    }

    @Override
    public void setUpOperation() {
    }
  };

  @Test
  public void testGetTableNames() throws Exception {
    final Connection connection = dbOperation.getConnection();
    List<String> tableNames = dbOperation.getTableNames(connection);

    Assert.assertTrue(tableNames.isEmpty());

    final Statement statement = connection.createStatement();
    dbOperation.executeScript("before.sql", statement);

    tableNames = dbOperation.getTableNames(connection);

    Assert.assertTrue(tableNames.contains("USER_TABLE"));
    Assert.assertTrue(tableNames.contains("ADDRESS_TABLE"));

    dbOperation.executeScript("after.sql", statement);

    statement.close();
  }

  @Test(expected = SQLException.class)
  public void testExecuteScript() throws Exception {
    try (final Statement statement = dbOperation.getConnection().createStatement()) {
      dbOperation.executeScript("exception.sql", statement);
    }
  }

  @Test(expected = SQLException.class)
  public void testExecuteScript_UnknownFileName() throws Exception {
    // expect logging and not an NullPointerException
    dbOperation.executeScript("unknown.sql", null);
  }

  @After
  public void tearDown() throws Exception {
    dbOperation.closeConnection();
  }
}
