package org.needle4j.junit;

import java.sql.SQLException;

import org.needle4j.db.operation.AbstractDBOperation;
import org.needle4j.db.operation.JdbcConfiguration;

public class DBOperationImpl extends AbstractDBOperation {

    public DBOperationImpl(JdbcConfiguration jdbcConfiguration) {
        super(jdbcConfiguration);
    }

    @Override
    public void setUpOperation() throws SQLException {
        throw new RuntimeException("executed");

    }

    @Override
    public void tearDownOperation() throws SQLException {
        throw new RuntimeException("executed");

    }
}
