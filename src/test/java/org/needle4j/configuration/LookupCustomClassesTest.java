package org.needle4j.configuration;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LookupCustomClassesTest {
  private static final String KEY = "FOO";

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailWhenConfigurationIsNull() {
    new LookupCustomClasses(null);
  }

  @Test
  public void shouldLoadInjectionProviders() {
    final var value = String.join(", ", String.class.getCanonicalName(), "foo.Bar", " ", null, Integer.class.getCanonicalName());
    final Map<String, String> configurationProperties = Map.of(KEY, value);

    final LookupCustomClasses lookupCustomClasses = new LookupCustomClasses(configurationProperties);
    final Set<Class<Object>> providers = lookupCustomClasses.lookup(KEY);

    assertEquals(providers.size(), 2);
    assertTrue(providers.contains(String.class));
    assertTrue(providers.contains(Integer.class));
  }
}
