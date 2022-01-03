package org.needle4j.injection.inheritance;

import org.needle4j.MyComponent;

import javax.inject.Inject;

public class DerivedComponent extends BaseComponent {

  @Inject
  private MyComponent componentFieldInjectionDerived;

  private MyComponent componentSetterInjectionDerived;

  @Override
  public MyComponent getComponentByFieldInjection() {
    return componentFieldInjectionDerived;
  }

  public MyComponent getComponentFromBaseByFieldInjection() {
    return super.getComponentByFieldInjection();
  }

  @Inject
  public void setComponentBySetter(final MyComponent component) {
    componentSetterInjectionDerived = component;
  }

  @Override
  public MyComponent getComponentBySetter() {
    return componentSetterInjectionDerived;
  }

  public MyComponent getComponentFromBaseBySetter() {
    return super.getComponentBySetter();
  }
}
