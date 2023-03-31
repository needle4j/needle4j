package org.needle4j.injection;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.junit.NeedleRule;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.needle4j.injection.InjectionProviders.providerForNamedInstance;

public class InjectionProvidersNamedInstanceInjectionProviderTest {
  private static final String FOO = "foo";

  public static class SomeType {
  }

  private final SomeType providedNamedInstance = new SomeType();

  @Rule
  public final NeedleRule needle = new NeedleRule(providerForNamedInstance(FOO, providedNamedInstance));

  @Inject
  private SomeType mockInstance;

  @Inject
  @Named(FOO)
  private SomeType namedInstance;

  @Test
  public void shouldInjectNamedInstance() {
    assertEquals(namedInstance, providedNamedInstance);
  }

  @Test
  public void shouldInjectDefaultInstance() {
    assertNotEquals(mockInstance, providedNamedInstance);
  }
}
