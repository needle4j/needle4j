package org.needle4j.db;

import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionTargetInformation;
import org.needle4j.injection.InjectionVerifier;

import javax.persistence.EntityManager;

class EntityManagerProvider implements InjectionProvider<EntityManager> {
  private final DatabaseTestcase databaseTestcase;
  private final InjectionVerifier verifyer;

  public EntityManagerProvider(final DatabaseTestcase databaseTestcase) {
    this.databaseTestcase = databaseTestcase;
    verifyer = information -> information.getType() == EntityManager.class;
  }

  @Override
  public EntityManager getInjectedObject(final Class<?> type) {
    return databaseTestcase.getEntityManager();
  }

  @Override
  public boolean verify(final InjectionTargetInformation injectionTargetInformation) {
    return verifyer.verify(injectionTargetInformation);
  }

  @Override
  public Object getKey(final InjectionTargetInformation injectionTargetInformation) {
    return EntityManager.class;
  }
}
