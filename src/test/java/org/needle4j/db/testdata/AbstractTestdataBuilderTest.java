package org.needle4j.db.testdata;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.db.Address;
import org.needle4j.db.Person;
import org.needle4j.junit.DatabaseRule;

import jakarta.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.*;

public class AbstractTestdataBuilderTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void testSaveOrUpdate() throws Exception {
    new AddressTestDataBuilder(databaseRule.getEntityManager()).buildAndSave();

    List<Address> loadAllObjects = databaseRule.getTransactionHelper().loadAllObjects(Address.class);

    assertEquals(1, loadAllObjects.size());
  }

  @Test
  public void testHasEntityManager() throws Exception {

    assertFalse(new AddressTestDataBuilder().hasEntityManager());
    assertTrue(new AddressTestDataBuilder(databaseRule.getEntityManager()).hasEntityManager());

  }

  @Test(expected = IllegalStateException.class)
  public void testSaveOrUpdate_withOutEntityManager() throws Exception {
    new AddressTestDataBuilder().buildAndSave();
  }

  @Test
  public void testGetId() throws Exception {
    assertEquals(0, new AddressTestDataBuilder().getId());
    assertEquals(1, new AddressTestDataBuilder().getId());
    assertEquals(2, new AddressTestDataBuilder().getId());
  }

  @Test(expected = RuntimeException.class)
  public void testBuildAndSave_Unsuccessful() throws Exception {
    new AbstractTestdataBuilder<Person>(databaseRule.getEntityManager()) {

      @Override
      public Person build() {

        return null;
      }

    }.buildAndSave();
  }

  class AddressTestDataBuilder extends AbstractTestdataBuilder<Address> {

    public AddressTestDataBuilder() {
      super();
    }

    public AddressTestDataBuilder(EntityManager entityManager) {
      super(entityManager);
    }

    @Override
    public Address build() {

      Address address = new Address();

      address.setStreet("street");
      return address;
    }
  }

}
