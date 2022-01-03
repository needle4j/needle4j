package org.needle4j.db.operation.h2;

import org.needle4j.db.operation.JdbcConfiguration;
import org.needle4j.db.operation.hsql.AbstractDeleteOperation;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

/**
 * Delete everything from the DB: This cannot be done with the JPA, because the
 * order of deletion matters. Instead we directly use a JDBC connection.
 */
public class H2DeleteOperation extends AbstractDeleteOperation {
  public H2DeleteOperation(final JdbcConfiguration configuration) {
    super(configuration);
  }

  @Override
  protected final void setReferentialIntegrity(final boolean enable, final Statement statement) throws SQLException {
    final String command = "SET REFERENTIAL_INTEGRITY " + String.valueOf(enable).toUpperCase(Locale.ROOT);
    statement.execute(command);
  }
}
