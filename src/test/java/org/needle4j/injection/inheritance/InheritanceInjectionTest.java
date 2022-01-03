package org.needle4j.injection.inheritance;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.MyComponent;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class InheritanceInjectionTest {

  @Rule
  public NeedleRule rule = new NeedleRule();

  @ObjectUnderTest
  private DerivedComponent derivedComponent;

  @ObjectUnderTest
  @InjectIntoMany
  private GraphDependencyComponent dependencyComponent;

  @Inject
  private MyComponent component;

  @Test
  public void testFieldInjection_SameMockObject() {
    assertNotNull(derivedComponent);
    assertSame(derivedComponent.getComponentFromBaseByFieldInjection(),
        derivedComponent.getComponentByFieldInjection());
    assertSame(component, derivedComponent.getComponentByFieldInjection());
  }

  @Test
  public void testSetterInjection_SameMockObject() {
    assertNotNull(derivedComponent);
    assertNotNull(derivedComponent.getComponentFromBaseBySetter());
    assertSame(derivedComponent.getComponentFromBaseBySetter(), derivedComponent.getComponentBySetter());
    assertSame(component, derivedComponent.getComponentBySetter());
  }

  @Test
  public void testGarphInjection() {

    final MyComponent componentByFieldInjection = derivedComponent.getComponentByFieldInjection();
    final MyComponent component = dependencyComponent.getComponent();

    assertSame(component, componentByFieldInjection);

  }

}
