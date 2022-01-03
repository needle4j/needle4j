package org.needle4j.db;

import org.junit.Assert;
import org.junit.Test;
import org.needle4j.injection.InjectionTargetInformation;
import org.needle4j.reflection.ReflectionUtil;

import javax.persistence.EntityManagerFactory;

public class EntityManagerFactoryProviderTest {
  private EntityManagerFactoryProvider entityManagerFactoryProvider = new EntityManagerFactoryProvider(
      new DatabaseTestcase());

  @SuppressWarnings("unused")
  private EntityManagerFactory entityManagerFactory;

  @Test
  public void testVerify() throws Exception {
    InjectionTargetInformation injectionTargetInformation = new InjectionTargetInformation(
        EntityManagerFactory.class, ReflectionUtil.getField(this.getClass(), "entityManagerFactory"));
    Assert.assertTrue(entityManagerFactoryProvider.verify(injectionTargetInformation));
  }

}
