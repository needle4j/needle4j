package org.needle4j.db.transaction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.needle4j.db.Address;
import org.needle4j.db.Person;
import org.needle4j.db.PersonTestdataBuilder;
import org.needle4j.db.User;
import org.needle4j.db.configuration.PersistenceConfigurationFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class TransactionHelperTest {

  private EntityManager entityManager;

  private TransactionHelper objectUnderTest;

  @Before
  public void setup() {
    PersistenceConfigurationFactory persistence = new PersistenceConfigurationFactory("TestDataModel");
    entityManager = persistence.getEntityManager();
    objectUnderTest = new TransactionHelper(entityManager);
  }

  @Test
  public void testLoadAllObjects_WithEntityName() throws Exception {
    Person entity = objectUnderTest.persist(new PersonTestdataBuilder().build());
    Assert.assertNotNull(entity.getId());

    List<Person> loadAllObjects = objectUnderTest.loadAllObjects(Person.class);
    Assert.assertEquals(1, loadAllObjects.size());

  }

  @Test
  public void testLoadAllObjects_WithDefaultEntityName() throws Exception {
    User entity = objectUnderTest.persist(new User());
    Assert.assertNotNull(entity.getId());

    List<User> loadAllObjects = objectUnderTest.loadAllObjects(User.class);
    Assert.assertEquals(1, loadAllObjects.size());
  }

  @Test
  public void testLoadAllObjects_EmptyResultList() throws Exception {
    List<Address> loadAllObjects = objectUnderTest.loadAllObjects(Address.class);
    Assert.assertEquals(0, loadAllObjects.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadAllObjects_WithUnknownEntity() throws Exception {
    objectUnderTest.persist(new Object());
  }
}
