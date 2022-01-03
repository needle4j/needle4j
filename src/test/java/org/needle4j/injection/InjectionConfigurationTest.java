package org.needle4j.injection;

import org.junit.Test;
import org.needle4j.mock.EasyMockProvider;
import org.needle4j.mock.MockProvider;
import org.needle4j.mock.MockitoProvider;

import static org.junit.Assert.*;

public class InjectionConfigurationTest {

  @Test
  public void testCreateMockProvider() throws Exception {
    Class<? extends MockProvider> lookupMockProviderClass = EasyMockProvider.class;
    InjectionConfiguration injectionConfiguration = new InjectionConfiguration();
    MockProvider mockProvider = injectionConfiguration.createMockProvider(lookupMockProviderClass);
    assertTrue(mockProvider instanceof EasyMockProvider);
  }

  @Test
  public void canLookupMockitoProvider() throws Exception {
    assertNotNull(InjectionConfiguration.lookupMockProviderClass(MockitoProvider.class.getName()));
  }

  @Test
  public void canLookupEasyMockProvider() {
    assertNotNull(InjectionConfiguration.lookupMockProviderClass(EasyMockProvider.class.getName()));
  }

  @Test
  public void lookupMockProviderDefaultsToMockitoProvider() throws Exception {
    assertEquals(MockitoProvider.class, InjectionConfiguration.lookupMockProviderClass(null));
  }

}
