package org.needle4j.db.operation.hsql;

import org.needle4j.db.operation.JdbcConfiguration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

/**
 * Delete everything from the DB: This cannot be done with the JPA, because the
 * order of deletion matters. Instead we directly use a JDBC connection.
 */
public class HSQLDeleteOperation extends AbstractDeleteOperation {
  public HSQLDeleteOperation(final JdbcConfiguration configuration) {
    super(configuration);
  }

  @Override
  protected final void setReferentialIntegrity(final boolean enable, final Statement statement) throws SQLException {
    final int databaseMajorVersion = statement.getConnection().getMetaData().getDatabaseMajorVersion();
    final String referentialIntegrity = String.valueOf(enable).toUpperCase(Locale.ROOT);

    final String command = databaseMajorVersion < 2 ? "SET REFERENTIAL_INTEGRITY "
        : "SET DATABASE REFERENTIAL INTEGRITY ";
    statement.execute(command + referentialIntegrity);
  }
}
