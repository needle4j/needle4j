package org.needle4j.injection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

public class CustomInjectionProviderTest {

  @Rule
  public NeedleRule needleRule = new NeedleRule();

  @ObjectUnderTest
  private CustomInjectionTestComponent component;

  @Test
  public void testCustomeInjectionProvider() throws Exception {
    Assert.assertSame(CustomMapInjectionProvider.MAP, component.getMap());
  }

}
