package org.needle4j.db;

import org.needle4j.configuration.NeedleConfiguration;
import org.needle4j.configuration.PropertyBasedConfigurationFactory;
import org.needle4j.db.operation.DBOperation;
import org.needle4j.db.transaction.TransactionHelper;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionTargetInformation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Map;

/**
 * Base class for a database test case. Executes optional database operation on
 * test setup and tear down.
 * <p>
 * May be used as an injection provider for {@link EntityManager},
 * {@link EntityManagerFactory} and {@link EntityTransaction}.
 *
 * @see InjectionProvider
 * @see DBOperation
 */
public class DatabaseTestcase implements InjectionProvider<Object> {
  private final DatabaseTestcaseConfiguration configuration;

  private TransactionHelper transactionHelper;
  private DBOperation dbOperation;
  private final Map<Class<?>, InjectionProvider<?>> injectionProviderMap = Map.of(EntityManager.class, new EntityManagerProvider(this),
      EntityManagerFactory.class, new EntityManagerFactoryProvider(this),
      EntityTransaction.class, new EntityTransactionProvider(this),
      TransactionHelper.class, new TransactionHelperProvider(this));

  /**
   * Creates an instance of {@link DatabaseTestcase} with the global
   * configured persistence unit name and the global configured
   * {@link DBOperation}.
   *
   * @see DBOperation
   */
  public DatabaseTestcase() {
    configuration = new DatabaseTestcaseConfiguration(PropertyBasedConfigurationFactory.get());
  }

  /**
   * Creates an instance of {@link DatabaseTestcase} with the global
   * configured persistence unit name and overrides the global configured
   * {@link DBOperation} with the given database operation.
   *
   * @param dbOperation database operation to execute on test setup and tear down
   * @see DBOperation
   */
  public DatabaseTestcase(final DBOperation dbOperation) {
    this();
    this.dbOperation = dbOperation;
  }

  /**
   * Creates an instance of {@link DatabaseTestcase} with the given
   * persistence unit name and the global configured {@link DBOperation}.
   *
   * @param persistenceUnitName the name of the persistence unit
   * @see DBOperation
   */
  public DatabaseTestcase(final String persistenceUnitName) {
    configuration = new DatabaseTestcaseConfiguration(PropertyBasedConfigurationFactory.get(), persistenceUnitName);
  }

  /**
   * Creates an instance of {@link DatabaseTestcase} with the given
   * persistence unit name and overrides the global configured
   * {@link DBOperation} with the given database operation.
   *
   * @param persistenceUnitName the name of the persistence unit
   * @param dbOperation         database operation to execute on test setup and tear down
   * @see DBOperation
   */
  public DatabaseTestcase(final String persistenceUnitName, final DBOperation dbOperation) {
    this(persistenceUnitName);
    this.dbOperation = dbOperation;
  }

  protected DatabaseTestcase(final NeedleConfiguration needleConfiguration) {
    configuration = new DatabaseTestcaseConfiguration(needleConfiguration, needleConfiguration.getPersistenceunitName());
  }

  /**
   * Execute tear down database operation, if configured.
   *
   * @throws Exception thrown if an error occurs
   */
  protected void after() throws Exception {
    final DBOperation operation = getDBOperation();

    if (operation != null) {
      operation.tearDownOperation();
    }

    getEntityManager().clear();
  }

  /**
   * Execute setup database operation, if configured.
   *
   * @throws Exception thrown if an error occurs
   */
  protected void before() throws Exception {
    final DBOperation operation = getDBOperation();
    if (operation != null) {
      operation.setUpOperation();
    }
  }

  private DBOperation getDBOperation() {
    return dbOperation != null ? dbOperation : configuration.getDBOperation();
  }

  /**
   * Returns the {@link EntityManager}.
   *
   * @return {@link EntityManager}.
   */
  public EntityManager getEntityManager() {
    return configuration.getEntityManager();
  }

  /**
   * Returns the {@link EntityManagerFactory}.
   *
   * @return {@link EntityManagerFactory}.
   */
  public EntityManagerFactory getEntityManagerFactory() {
    return configuration.getEntityManagerFactory();
  }

  /**
   * Returns an instance of {@link TransactionHelper}
   *
   * @return {@link TransactionHelper}
   * @see TransactionHelper
   */
  public TransactionHelper getTransactionHelper() {
    if (transactionHelper == null) {
      transactionHelper = new TransactionHelper(getEntityManager());
    }

    return transactionHelper;
  }

  @Override
  public Object getInjectedObject(final Class<?> injectionPointType) {
    return getInjectionProvider(injectionPointType).getInjectedObject(injectionPointType);
  }

  @Override
  public boolean verify(final InjectionTargetInformation injectionTargetInformation) {
    final InjectionProvider<?> injectionProvider = getInjectionProvider(injectionTargetInformation.getType());
    return injectionProvider != null && injectionProvider.verify(injectionTargetInformation);
  }

  @Override
  public Object getKey(final InjectionTargetInformation injectionTargetInformation) {
    return getInjectionProvider(injectionTargetInformation.getType()).getKey(injectionTargetInformation);
  }

  private InjectionProvider<?> getInjectionProvider(final Class<?> type) {
    return injectionProviderMap.get(type);
  }
}
