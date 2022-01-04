package org.needle4j.injection;

import org.junit.Test;
import org.needle4j.MyComponent;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.needle4j.injection.InjectionProviders.providerForInstance;

public class CustomMyComponentInjectionProviderInstancesSupplier implements InjectionProviderInstancesSupplier {
  public static final String ID = UUID.randomUUID().toString();

  @Override
  public Set<InjectionProvider<?>> get() {
    return Collections.singleton(providerForInstance((MyComponent) () -> ID));
  }

  @Test
  public void shouldReturnMyFooComponent() {
    assertEquals(((MyComponent) get().iterator().next().getInjectedObject(null)).testMock(), ID);
  }
}
