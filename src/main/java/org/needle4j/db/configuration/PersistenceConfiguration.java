package org.needle4j.db.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * Abstraction for bootstrapping {@link EntityManagerFactory} and
 * {@link EntityManager}.
 */
interface PersistenceConfiguration {
  /**
   * Returns an {@link EntityManager} instance.
   *
   * @return entityManager
   */
  EntityManager getEntityManager();

  /**
   * Returns the EntityManagerFactory.
   *
   * @return EntityManagerFactory
   */
  EntityManagerFactory getEntityManagerFactory();
}
