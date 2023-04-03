package org.needle4j.injection.inheritance;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.MyComponent;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

import jakarta.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class InheritanceConstructorInjectionTest {

  @Rule
  public NeedleRule rule = new NeedleRule();

  @ObjectUnderTest
  private ConstructorInjectionDerivedComponent derivedComponent;

  @Inject
  private MyComponent component;

  @Test
  public void testFieldInjection_SameMockObject() {
    assertNotNull(derivedComponent);
    assertNotNull(component);
    assertSame(derivedComponent.getMyComponentFromBase(), derivedComponent.getMyComponent());
    assertSame(component, derivedComponent.getMyComponent());
  }

}
