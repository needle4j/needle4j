package org.needle4j.db.transaction;

import jakarta.persistence.EntityManager;

/**
 * Default implementation of {@link Runnable}. Does nothing.
 */
public abstract class VoidRunnable implements Runnable<Object> {
  /**
   * {@inheritDoc}
   */
  @Override
  public final Object run(final EntityManager entityManager) throws Exception {
    doRun(entityManager);
    return null;
  }

  /**
   * Hook method inside run().
   *
   * @param entityManager entityManager
   * @throws Exception when something failed
   */
  public abstract void doRun(EntityManager entityManager) throws Exception;
}
