package org.needle4j.db.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static jakarta.persistence.SharedCacheMode.DISABLE_SELECTIVE;
import static jakarta.persistence.ValidationMode.AUTO;

public class HibernatePersistenceUnitInfo implements PersistenceUnitInfo {
  public static String JPA_VERSION = "2.1";

  private final String persistenceUnitName;
  private final List<String> managedClassNames;
  private final Properties properties;

  public HibernatePersistenceUnitInfo(final String persistenceUnitName, final List<String> managedClassNames, final Properties properties) {
    this.persistenceUnitName = persistenceUnitName;
    this.managedClassNames = managedClassNames;
    this.properties = properties;
  }

  @Override
  public String getPersistenceUnitName() {
    return persistenceUnitName;
  }

  @Override
  public String getPersistenceProviderClassName() {
    return HibernatePersistenceProvider.class.getName();
  }

  @Override
  public PersistenceUnitTransactionType getTransactionType() {
    return PersistenceUnitTransactionType.RESOURCE_LOCAL;
  }

  @Override
  public DataSource getJtaDataSource() {
    return null;
  }

  @Override
  public DataSource getNonJtaDataSource() {
    return null;
  }

  @Override
  public List<String> getMappingFileNames() {
    return Collections.emptyList();
  }

  @Override
  public List<URL> getJarFileUrls() {
    return Collections.emptyList();
  }

  @Override
  public URL getPersistenceUnitRootUrl() {
    return null;
  }

  @Override
  public List<String> getManagedClassNames() {
    return managedClassNames;
  }

  @Override
  public boolean excludeUnlistedClasses() {
    return true;
  }

  @Override
  public SharedCacheMode getSharedCacheMode() {
    return DISABLE_SELECTIVE;
  }

  @Override
  public ValidationMode getValidationMode() {
    return AUTO;
  }

  @Override
  public Properties getProperties() {
    return properties;
  }

  @Override
  public String getPersistenceXMLSchemaVersion() {
    return JPA_VERSION;
  }

  @Override
  public ClassLoader getClassLoader() {
    return ClassLoader.getSystemClassLoader();
  }

  @Override
  public void addTransformer(final ClassTransformer transformer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ClassLoader getNewTempClassLoader() {
    return getClassLoader();
  }
}