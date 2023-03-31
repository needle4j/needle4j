package org.needle4j.configuration;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.MyComponent;
import org.needle4j.injection.CustomMyComponentInjectionProviderInstancesSupplier;
import org.needle4j.junit.NeedleRule;

import jakarta.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.needle4j.junit.NeedleBuilders.needleRule;

/**
 * The needle-mockito properties file sets the custom.instances.supplier.classes
 * property to {@link CustomMyComponentInjectionProviderInstancesSupplier}. The
 * test verifies, that the component instance defined in the supplier is used
 * for injection.
 */
public class LoadInstancesSupplierFromProperties {
  @Rule
  public final NeedleRule needle = needleRule("needle-mockito").build();

  @SuppressWarnings("CdiInjectionPointsInspection")
  @Inject
  private MyComponent component;

  @Test
  public void shouldInjectMyComponentWithFoo() {
    assertEquals(component.testMock(), CustomMyComponentInjectionProviderInstancesSupplier.ID);
  }
}
