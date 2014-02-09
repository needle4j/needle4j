package org.needle4j.injection;

import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

public class InjectionPriorityTest {

  private final Map<Object, Object> map = new HashMap<Object, Object>();

  private final InjectionProvider<Map<Object, Object>> injectionProvider = new CustomMapInjectionProvider() {
    @Override
    public Map<Object, Object> getInjectedObject(final java.lang.Class<?> injectionPointType) {
      return map;
    };
  };

  @Rule
  public NeedleRule needleRule = new NeedleRule(injectionProvider);

  @ObjectUnderTest
  private CustomInjectionTestComponent component;

  @Test
  public void testInjectionProviderPriority() throws Exception {
    assertSame(map, component.getMap());
  }

}
