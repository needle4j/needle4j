package org.needle4j.db.configuration;

import org.junit.Assert;
import org.junit.Test;

import org.needle4j.db.Address;
import org.needle4j.db.Person;

public class PersistenceConfigurationFactoryTest {

	@Test
	public void testEqualsWithClasses() throws Exception {
		Class<?>[] personClazzes = { Person.class, Address.class };
		PersistenceConfigurationFactory persistenceConfigurationFactory1 = new PersistenceConfigurationFactory(
		        personClazzes);

		Class<?>[] clazzes2 = { Person.class, Address.class };
		PersistenceConfigurationFactory persistenceConfigurationFactory2 = new PersistenceConfigurationFactory(clazzes2);

		Assert.assertSame(persistenceConfigurationFactory1.getEntityManagerFactory(),
		        persistenceConfigurationFactory2.getEntityManagerFactory());

		Assert.assertSame(persistenceConfigurationFactory1.getEntityManager(),
		        persistenceConfigurationFactory2.getEntityManager());
	}

	@Test
	public void testEqualsWithPersistenceUnitName() throws Exception {
		PersistenceConfigurationFactory persistenceConfigurationFactory1 = new PersistenceConfigurationFactory(
		        "TestDataModel");

		PersistenceConfigurationFactory persistenceConfigurationFactory2 = new PersistenceConfigurationFactory(
		        "TestDataModel");

		Assert.assertSame(persistenceConfigurationFactory1.getEntityManagerFactory(),
		        persistenceConfigurationFactory1.getEntityManagerFactory());

		Assert.assertSame(persistenceConfigurationFactory1.getEntityManager(),
		        persistenceConfigurationFactory2.getEntityManager());
	}
}
