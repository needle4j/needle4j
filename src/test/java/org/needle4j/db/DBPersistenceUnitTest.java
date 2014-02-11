package org.needle4j.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.db.transaction.TransactionHelper;
import org.needle4j.junit.DatabaseRule;

public class DBPersistenceUnitTest {

    @Rule
    public DatabaseRule db = new DatabaseRule("TestDataModel");

    @Test
    public void testDB_withPersistenceUnit() throws Exception {
        final Person person = new Person();
        final EntityManager entityManager = db.getEntityManager();

        person.setMyName("My Name");

        assertNotNull(db);
        assertNotNull(entityManager);

        final EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(person);

        final Person fromDB = entityManager.find(Person.class, person.getId());

        assertSame(person, fromDB);
        tx.commit();
    }

    @Test
    public void testTransactions() throws Exception {
        final Person myEntity = new Person();
        final EntityManager entityManager = db.getEntityManager();

        myEntity.setMyName("My Name");

        final TransactionHelper transactionHelper = new TransactionHelper(entityManager);

        transactionHelper.saveObject(myEntity);
        final Person fromDb = transactionHelper.loadObject(Person.class, myEntity.getId());

        assertFalse(myEntity == fromDb);
    }
}
