package org.needle4j.junit;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.needle4j.configuration.NeedleConfiguration;
import org.needle4j.db.DatabaseTestcase;
import org.needle4j.db.operation.DBOperation;

/**
 * The {@link DatabaseRule} provides access to the configured Database and
 * execute optional configured {@link DBOperation} before and after a test.
 * 
 * <pre>
 * public class EntityTestcase {
 *     &#064;Rule
 *     public DatabaseRule databaseRule = new DatabaseRule();
 * 
 *     &#064;Test
 *     public void testPersist() throws Exception {
 *         User user = new User();
 *         // ...
 *         databaseRule.getEntityMnager().persist(user);
 *     }
 * }
 * </pre>
 * 
 * @see DatabaseTestcase
 * @see DBOperation
 * 
 */
public class DatabaseRule extends DatabaseTestcase implements MethodRule {

    /**
     * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase()
     */
    public DatabaseRule() {
        super();
    }

    /**
     * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(Class...)
     */
    @Deprecated
    public DatabaseRule(final Class<?>... clazzes) {
        super(clazzes);
    }

    /**
     * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(DBOperation,
     *      Class...)
     */
    @Deprecated
    public DatabaseRule(final DBOperation dbOperation, final Class<?>... clazzes) {
        super(dbOperation, clazzes);
    }

    /**
     * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(DBOperation)
     */
    public DatabaseRule(final DBOperation dbOperation) {
        super(dbOperation);
    }

    /**
     * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(String,
     *      DBOperation)
     */
    public DatabaseRule(final String persistenceUnitName, final DBOperation dbOperation) {
        super(persistenceUnitName, dbOperation);
    }

    /**
     * @see org.needle4j.db.DatabaseTestcase#DatabaseTestcase(String)
     */
    public DatabaseRule(final String persistenceUnitName) {
        super(persistenceUnitName);
    }

    DatabaseRule(final NeedleConfiguration configuration) {
        super(configuration);
    }

    @Override
    public final Statement apply(final Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    before();
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }
}
