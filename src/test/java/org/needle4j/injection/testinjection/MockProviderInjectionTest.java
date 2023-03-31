package org.needle4j.injection.testinjection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.junit.NeedleRule;
import org.needle4j.mock.EasyMockProvider;

import jakarta.inject.Inject;

public class MockProviderInjectionTest {

  @Rule
  public NeedleRule needleRule = new NeedleRule();

  @Inject
  private EasyMockProvider mockProvider;

  @Test
  public void testMockProviderInjection() throws Exception {
    Assert.assertNotNull(mockProvider);
  }

}
