package org.needle4j.db.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Execute before and after sql scripts in test setup and tear down.
 */
public class ExecuteScriptOperation extends AbstractDBOperation {

  private static final Logger LOG = LoggerFactory.getLogger(ExecuteScriptOperation.class);

  public ExecuteScriptOperation(JdbcConfiguration jdbcConfiguration) {
    super(jdbcConfiguration);
  }

  private static final String BEFORE_SCRIPT_NAME = "before.sql";
  private static final String AFTER_SCRIPT_NAME = "after.sql";

  /**
   * Execute <tt>before.sql</tt> script in test setup.
   *
   * @throws SQLException if a database access error occurs
   */
  @Override
  public void setUpOperation() throws SQLException {
    execute(BEFORE_SCRIPT_NAME);
  }

  /**
   * Execute <tt>after.sql</tt> script in test tear down.
   *
   * @throws SQLException if a database access error occurs
   */
  @Override
  public void tearDownOperation() throws SQLException {
    execute(AFTER_SCRIPT_NAME);
  }

  private void execute(final String filename) throws SQLException {
    try (final Connection connection = getConnection(); final Statement statement = connection.createStatement()) {
      executeScript(filename, statement);

      commit();
    } catch (final SQLException e) {
      LOG.error(e.getMessage(), e);

      try {
        rollback();
      } catch (final SQLException e1) {
        LOG.error(e1.getMessage(), e1);
      }

      throw e;
    }
  }
}
