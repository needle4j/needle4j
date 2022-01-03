package org.needle4j.db;

import org.junit.Test;
import org.needle4j.db.transaction.TransactionHelper;
import org.needle4j.injection.InjectionTargetInformation;
import org.needle4j.reflection.ReflectionUtil;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TransactionHelperProviderTest {
  private TransactionHelperProvider provider = new TransactionHelperProvider(new DatabaseTestcase());

  @SuppressWarnings("unused")
  private TransactionHelper helper;

  @Test
  public void testVerify() throws Exception {
    InjectionTargetInformation injectionTargetInformation = new InjectionTargetInformation(TransactionHelper.class,
        ReflectionUtil.getField(this.getClass(), "helper"));
    assertTrue(provider.verify(injectionTargetInformation));
    assertNotNull(provider.getInjectedObject(TransactionHelper.class));
  }

}
