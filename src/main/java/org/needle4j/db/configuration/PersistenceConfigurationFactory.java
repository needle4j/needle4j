package org.needle4j.db.configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory to create and access the configured persistence context.
 */
public final class PersistenceConfigurationFactory implements PersistenceConfiguration {
  private static final Map<PersistenceConfigurationFactory, PersistenceConfiguration> PERSISTENCE_MAP = new HashMap<>();

  private String persistenceUnit;
  private Class<?>[] entityClasses;

  public PersistenceConfigurationFactory(final String persistenceUnit) {
    this.persistenceUnit = persistenceUnit;
  }

  public PersistenceConfigurationFactory(final Class<?>... entityClasses) {
    this.entityClasses = Arrays.copyOf(entityClasses, entityClasses.length);
  }

  private PersistenceConfiguration getPersistenceConfiguration() {
    return getPersistenceConfiguration(this);
  }

  String getPersistenceUnit() {
    return persistenceUnit;
  }

  Class<?>[] getEntityClasses() {
    return entityClasses;
  }

  /**
   * Returns the {@link EntityManager} instance which is associated with the
   * configured persistence context.
   *
   * @return {@link EntityManager}
   */
  @Override
  public EntityManager getEntityManager() {
    return getPersistenceConfiguration().getEntityManager();
  }

  /**
   * Returns the {@link EntityManagerFactory}.
   *
   * @return {@link EntityManagerFactory}
   */
  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    return getPersistenceConfiguration().getEntityManagerFactory();
  }

  private static PersistenceConfiguration getPersistenceConfiguration(
      final PersistenceConfigurationFactory configuration) {
    PersistenceConfiguration result = PERSISTENCE_MAP.get(configuration);

    if (result == null) {
      result = createPersistenceConfiguration(configuration);
      PERSISTENCE_MAP.put(configuration, result);
    }

    return result;
  }

  private static PersistenceConfiguration createPersistenceConfiguration(final PersistenceConfigurationFactory configuration) {
    if (configuration.getPersistenceUnit() != null) {
      return new PersistenceUnitConfiguration(configuration.getPersistenceUnit());
    } else if (configuration.getEntityClasses() != null) {
      return new JpaEntityManagerFactory(configuration.getEntityClasses());
    } else {
      return null;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(entityClasses);
    result = prime * result + ((persistenceUnit == null) ? 0 : persistenceUnit.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    final PersistenceConfigurationFactory other = (PersistenceConfigurationFactory) obj;

    if (!Arrays.equals(entityClasses, other.entityClasses)) {
      return false;
    }

    if (persistenceUnit == null) {
      return other.persistenceUnit == null;
    } else {
      return persistenceUnit.equals(other.persistenceUnit);
    }
  }
}
