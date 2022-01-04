package org.needle4j.db.operation;

import org.needle4j.configuration.ConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * An abstract implementation of {@link DBOperation} with common jdbc
 * operations.
 */
public abstract class AbstractDBOperation implements DBOperation {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractDBOperation.class);

  private final JdbcConfiguration configuration;

  private Connection connection;

  public AbstractDBOperation(final JdbcConfiguration jdbcConfiguration) {
    this.configuration = jdbcConfiguration;
  }

  /**
   * Close the connection to the database.
   *
   * @throws SQLException if a database access error occurs
   */
  protected void closeConnection() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
      connection = null;
    }
  }

  /**
   * Commits the current transaction.
   *
   * @throws SQLException if a database access error occurs
   */
  protected void commit() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.commit();
    }
  }

  /**
   * Revoke the current transaction.
   *
   * @throws SQLException if a database access error occurs
   */
  protected void rollback() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.rollback();
    }
  }

  /**
   * Returns the names of all tables in the database by using
   * {@linkplain DatabaseMetaData}.
   *
   * @param connection the jdbc connection object
   * @return a {@link List} of all table names
   * @throws SQLException if a database access error occurs
   */
  protected List<String> getTableNames(final Connection connection) throws SQLException {
    final List<String> tables = new ArrayList<>();

    try (ResultSet resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"})) {
      while (resultSet.next()) {
        final String schema = resultSet.getString("TABLE_SCHEM");

        if (schema == null || !getIgnoredSchemas().contains(schema.toUpperCase(Locale.ROOT))) {
          final var tableName = resultSet.getString("TABLE_NAME");
          tables.add(tableName);
        }
      }
    }

    return tables;
  }

  /**
   * @return schemas to be ignored in upper case
   */
  protected List<String> getIgnoredSchemas() {
    return List.of("INFORMATION_SCHEMA");
  }

  private void executeScript(final BufferedReader script, final Statement statement) throws SQLException {
    long lineNo = 0;

    StringBuilder sql = new StringBuilder();
    String line;

    try {
      while ((line = script.readLine()) != null) {

        lineNo++;
        final String trimmedLine = line.trim();

        if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("--") && !trimmedLine.startsWith("//")) {
          if (trimmedLine.startsWith("/*")) {
            while ((line = script.readLine()) != null) {
              if (line.trim().endsWith("*/")) {
                LOG.debug("ignore " + line);
                break;
              }
            }
          } else {
            sql.append(trimmedLine);
            if (trimmedLine.endsWith(";")) {
              String sqlStatement = sql.toString();
              sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 1);
              LOG.info(sqlStatement);

              statement.execute(sqlStatement);
              sql = new StringBuilder();
            }
          }
        }
      }
    } catch (final Exception e) {
      throw new SQLException("Error during import script execution at line " + lineNo, e);
    }
  }

  /**
   * Execute the given sql script.
   *
   * @param filename  the filename of the sql script
   * @param statement the {@link Statement} to be used for executing a SQL
   *                  statement.
   * @throws SQLException if a database access error occurs
   */
  protected void executeScript(final String filename, final Statement statement) throws SQLException {
    final var message = "Executing sql script: " + filename;
    LOG.info(message);

    try (final InputStream fileInputStream = ConfigurationLoader.loadResource(filename);
         final BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
      executeScript(reader, statement);
    } catch (final IOException e) {
      LOG.error(message, e);
      throw new SQLException(message, e);
    }
  }

  /**
   * Returns the sql connection object. If there is no connection a new connection is established.
   *
   * @return the sql connection object
   * @throws SQLException if a database access error occurs
   */
  protected Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      final var jdbcDriver = configuration.getJdbcDriver();

      try {
        Class.forName(jdbcDriver);
      } catch (final NullPointerException npe) {
        throw new RuntimeException("Error while looking up JDBC driver class. JDBC driver is not configured.", npe);
      } catch (final ClassNotFoundException e) {
        throw new RuntimeException("JDBC driver " + jdbcDriver + "not found", e);
      }

      connection = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getJdbcUser(),
          configuration.getJdbcPassword());
      connection.setAutoCommit(false);
    }

    return connection;
  }
}
