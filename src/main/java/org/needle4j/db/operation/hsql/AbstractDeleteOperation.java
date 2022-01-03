package org.needle4j.db.operation.hsql;

import org.needle4j.db.operation.AbstractDBOperation;
import org.needle4j.db.operation.JdbcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Delete everything from the DB: This cannot be done with the JPA, because the
 * order of deletion matters. Instead we directly use a JDBC connection.
 */
public abstract class AbstractDeleteOperation extends AbstractDBOperation {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDeleteOperation.class);

    public AbstractDeleteOperation(final JdbcConfiguration configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc} No operation implementation.
     */
    @Override
    public void setUpOperation() throws SQLException {
    }

    /**
     * {@inheritDoc}. Delete all data from all tables given by
     * {@link #getTableNames(Connection)}.
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void tearDownOperation() throws SQLException {
        Statement statement = null;

        try {
            final Connection connection = getConnection();

            statement = connection.createStatement();

            disableReferentialIntegrity(statement);
            final List<String> tableNames = getTableNames(getConnection());

            deleteContent(tableNames, statement);

        } catch (final SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                rollback();
            } catch (final SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            try {
                enableReferentialIntegrity(statement);

                if (statement != null) {
                    statement.close();
                }
                commit();

                closeConnection();
            } catch (final Exception e) {
                rollback();
                LOG.error(e.getMessage(), e);
            }
        }

    }

    /**
     * Disables the referential constraints of the database, e.g foreign keys.
     *
     * @throws SQLException - if a database access error occurs
     */
    protected void disableReferentialIntegrity(final Statement statement) throws SQLException {
        setReferentialIntegrity(false, statement);
    }

    /**
     * Enables the referential constraints of the database, e.g foreign keys.
     *
     * @throws SQLException - if a database access error occurs
     */
    protected void enableReferentialIntegrity(final Statement statement) throws SQLException {
        setReferentialIntegrity(true, statement);
    }

    protected abstract void setReferentialIntegrity(final boolean enable, final Statement statement) throws SQLException;

    /**
     * Deletes all contents from the given tables.
     *
     * @param tables    a {@link List} of table names who are to be deleted.
     * @param statement the {@link Statement} to be used for executing a SQL
     *                  statement.
     * @throws SQLException - if a database access error occurs
     */
    protected void deleteContent(final List<String> tables, final Statement statement) throws SQLException {
        final ArrayList<String> tempTables = new ArrayList<>(tables);

        // Loop until all data is deleted: we don't know the correct DROP
        // order, so we have to retry upon failure
        while (!tempTables.isEmpty()) {
            final int sizeBefore = tempTables.size();
            for (final ListIterator<String> iterator = tempTables.listIterator(); iterator.hasNext(); ) {
                final String table = iterator.next();

                try {
                    statement.executeUpdate("DELETE FROM " + table);
                    iterator.remove();
                } catch (final SQLException exc) {
                    LOG.warn("Ignored exception: " + exc.getMessage() + ". WILL RETRY.");
                }
            }
            if (tempTables.size() == sizeBefore) {
                throw new AssertionError("unable to clean tables " + tempTables);
            }
        }
    }
}
