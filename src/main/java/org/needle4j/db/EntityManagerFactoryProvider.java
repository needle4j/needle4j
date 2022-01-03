package org.needle4j.db;

import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionTargetInformation;
import org.needle4j.injection.InjectionVerifier;

import javax.persistence.EntityManagerFactory;

class EntityManagerFactoryProvider implements InjectionProvider<EntityManagerFactory> {

  private final DatabaseTestcase databaseTestcase;
  private final InjectionVerifier verifier;

  EntityManagerFactoryProvider(final DatabaseTestcase databaseTestcase) {
    super();
    this.databaseTestcase = databaseTestcase;
    verifier = new InjectionVerifier() {

      @Override
      public boolean verify(final InjectionTargetInformation information) {
        return information.getType() == EntityManagerFactory.class;
      }
    };

  }

  @Override
  public EntityManagerFactory getInjectedObject(final Class<?> type) {
    return databaseTestcase.getEntityManagerFactory();
  }

  @Override
  public boolean verify(final InjectionTargetInformation injectionTargetInformation) {
    return verifier.verify(injectionTargetInformation);
  }

  @Override
  public Object getKey(final InjectionTargetInformation injectionTargetInformation) {
    return EntityManagerFactory.class;
  }
}
