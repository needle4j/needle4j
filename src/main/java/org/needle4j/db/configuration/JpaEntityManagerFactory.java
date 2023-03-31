package org.needle4j.db.configuration;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.needle4j.configuration.PropertyBasedConfigurationFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import java.util.*;
import java.util.stream.Collectors;

public class JpaEntityManagerFactory implements PersistenceConfiguration {
  private final List<String> entityClasses;

  private EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;

  public JpaEntityManagerFactory(final Class<?>... entityClasses) {
    this.entityClasses = Arrays.stream(entityClasses).map(Class::getName)
        .collect(Collectors.toList());
  }

  @Override
  public EntityManager getEntityManager() {
    if (entityManager == null) {
      entityManager = EntityManagerProxyFactory.createProxy(getEntityManagerFactory().createEntityManager());
    }

    return entityManager;
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory == null) {
      final var needleConfiguration = PropertyBasedConfigurationFactory.get();
      final PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(needleConfiguration.getPersistenceunitName());
      @SuppressWarnings({"unchecked", "rawtypes"}) final Map<String, Object> configuration = new HashMap<>((Map<String, Object>) ((Map) getProperties()));

      entityManagerFactory = new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration).build();
    }

    return entityManagerFactory;
  }

  protected Properties getProperties() {
    final var needleConfiguration = PropertyBasedConfigurationFactory.get();
    final var properties = new Properties();
    properties.put("hibernate.connection.driver_class", needleConfiguration.getJdbcDriver());
    properties.put("hibernate.connection.url", needleConfiguration.getJdbcUrl());
    return properties;
  }

  protected PersistenceUnitInfo getPersistenceUnitInfo(String name) {
    return new HibernatePersistenceUnitInfo(name, entityClasses, getProperties());
  }
}