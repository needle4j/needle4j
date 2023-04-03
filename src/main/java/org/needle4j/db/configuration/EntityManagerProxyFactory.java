package org.needle4j.db.configuration;

import jakarta.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

final class EntityManagerProxyFactory {
  private EntityManagerProxyFactory() {
  }

  static EntityManager createProxy(final EntityManager real) {
    return (EntityManager) Proxy.newProxyInstance(PersistenceConfigurationFactory.class.getClassLoader(),
        new Class[]{EntityManager.class}, (proxy, method, args) -> {
          if (method.getName().equals("close")) {
            throw new RuntimeException("you are not allowed to explicitely close this EntityManager");
          }

          try {
            return method.invoke(real, args);
          } catch (final InvocationTargetException e) {
            throw e.getCause();
          }
        });
  }
}
