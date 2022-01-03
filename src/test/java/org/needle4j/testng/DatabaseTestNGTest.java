package org.needle4j.testng;

import org.junit.Assert;
import org.needle4j.db.Address;
import org.needle4j.db.Person;
import org.testng.annotations.Test;

public class DatabaseTestNGTest extends DatabaseTestcase {

  public DatabaseTestNGTest() {
    super(Person.class, Address.class);
  }

  @Test
  public void testGetDBAccess() throws Exception {
    Assert.assertNotNull(getEntityManagerFactory());
    Assert.assertNotNull(getEntityManager());
  }

}
