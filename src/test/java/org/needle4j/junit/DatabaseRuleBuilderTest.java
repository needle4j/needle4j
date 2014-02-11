package org.needle4j.junit;

import static org.needle4j.junit.NeedleBuilders.databaseRule;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.model.Statement;

public class DatabaseRuleBuilderTest {

    @Test(expected = PersistenceException.class)
    public void testCustomConfiguration_WithNoPersistenceProvider() throws Exception {
        DatabaseRule databaseRule = databaseRule("database-custom").build();
        databaseRule.getEntityManagerFactory();
    }

    @Test(expected = PersistenceException.class)
    public void testWithPersitenceUnitName_NoPersistenceProvider() throws Exception {
        DatabaseRule databaseRule = databaseRule().withPersistenceUnit("name").build();
        databaseRule.getEntityManagerFactory();
    }

    @Test
    public void testDBOperation() throws Exception {
        DatabaseRule build = databaseRule().withDBOperation(DBOperationImpl.class).build();
        Statement base = new Statement() {

            @Override
            public void evaluate() throws Throwable {
            }
        };
        Statement apply = build.apply(base, null, this);
        try {
            apply.evaluate();
        } catch (Throwable e) {
            Assert.assertEquals("executed", e.getMessage());
        }
    }

}
