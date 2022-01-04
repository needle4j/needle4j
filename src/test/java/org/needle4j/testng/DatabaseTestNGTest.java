package org.needle4j.testng;

import org.junit.Assert;
import org.testng.annotations.Test;

public class DatabaseTestNGTest extends DatabaseTestcase {
  @Test
  public void testGetDBAccess() {
    Assert.assertNotNull(getEntityManagerFactory());
    Assert.assertNotNull(getEntityManager());
  }
}
