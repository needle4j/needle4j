package org.needle4j.db.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Creates an {@link EntityManagerFactory} and {@link EntityManager} for a named
 * persistence unit.
 */
class PersistenceUnitConfiguration implements PersistenceConfiguration {
  private final EntityManagerFactory factory;
  private final EntityManager entityManager;

  /**
   * Creates an {@link EntityManagerFactory} and {@link EntityManager} for the
   * named persistence unit.
   *
   * @param persistenceUnitName the name of the persistence unit
   */
  public PersistenceUnitConfiguration(final String persistenceUnitName) {
    factory = Persistence.createEntityManagerFactory(persistenceUnitName);
    entityManager = EntityManagerProxyFactory.createProxy(factory.createEntityManager());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    return factory;
  }
}
