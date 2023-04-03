package org.needle4j.db;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.junit.DatabaseRule;

import jakarta.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DbTestcaseHbmCfgTest {
  @Rule
  public DatabaseRule db = new DatabaseRule();

  @Test
  public void testPersist() {
    final Person person = new Person();
    final EntityManager entityManager = db.getEntityManager();

    person.setMyName("My Name");

    assertNotNull(db);
    assertNotNull(entityManager);

    entityManager.getTransaction().begin();
    entityManager.persist(person);

    final Person fromDB = entityManager.find(Person.class, person.getId());

    assertEquals(fromDB.getMyName(), person.getMyName());
    entityManager.getTransaction().commit();
  }
}
