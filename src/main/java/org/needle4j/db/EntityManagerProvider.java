package org.needle4j.db;

import javax.persistence.EntityManager;

import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionTargetInformation;
import org.needle4j.injection.InjectionVerifier;

class EntityManagerProvider implements InjectionProvider<EntityManager> {

    private final DatabaseTestcase databaseTestcase;

    private final InjectionVerifier verifyer;

    public EntityManagerProvider(final DatabaseTestcase databaseTestcase) {
        super();
        this.databaseTestcase = databaseTestcase;
        verifyer = new InjectionVerifier() {

            @Override
            public boolean verify(final InjectionTargetInformation information) {
                if (information.getType() == EntityManager.class) {
                    return true;
                }
                return false;
            }
        };

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
