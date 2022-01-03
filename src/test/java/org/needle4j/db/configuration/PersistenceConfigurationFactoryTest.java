package org.needle4j.db.configuration;

import org.junit.Test;
import org.needle4j.db.Address;
import org.needle4j.db.Person;

import static org.junit.Assert.assertSame;

public class PersistenceConfigurationFactoryTest {
  @Test
  public void testEqualsWithClasses() {
    final Class<?>[] personClazzes = {Person.class, Address.class};
    final PersistenceConfigurationFactory persistenceConfigurationFactory1 = new PersistenceConfigurationFactory(personClazzes);

    final Class<?>[] clazzes2 = {Person.class, Address.class};
    final PersistenceConfigurationFactory persistenceConfigurationFactory2 = new PersistenceConfigurationFactory(clazzes2);

    assertSame(persistenceConfigurationFactory1.getEntityManagerFactory(), persistenceConfigurationFactory2.getEntityManagerFactory());
    assertSame(persistenceConfigurationFactory1.getEntityManager(), persistenceConfigurationFactory2.getEntityManager());
  }

  @Test
  public void testEqualsWithPersistenceUnitName() {
    final PersistenceConfigurationFactory persistenceConfigurationFactory1 = new PersistenceConfigurationFactory(
        "TestDataModel");

    final PersistenceConfigurationFactory persistenceConfigurationFactory2 = new PersistenceConfigurationFactory(
        "TestDataModel");

    assertSame(persistenceConfigurationFactory1.getEntityManagerFactory(), persistenceConfigurationFactory1.getEntityManagerFactory());
    assertSame(persistenceConfigurationFactory1.getEntityManager(), persistenceConfigurationFactory2.getEntityManager());
  }
}
