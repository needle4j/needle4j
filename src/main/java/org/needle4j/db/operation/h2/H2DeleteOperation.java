package org.needle4j.db.operation.h2;

import org.needle4j.db.operation.JdbcConfiguration;
import org.needle4j.db.operation.hsql.HSQLDeleteOperation;

/**
 * Delete everything from the DB: This cannot be done with the JPA, because the
 * order of deletion matters. Instead we directly use a JDBC connection.
 */
public class H2DeleteOperation extends HSQLDeleteOperation {

    public H2DeleteOperation(JdbcConfiguration configuration) {
        super(configuration);
    }

}
