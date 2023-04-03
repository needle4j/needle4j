package org.needle4j.injection.inheritance;

import org.needle4j.MyComponent;

import jakarta.inject.Inject;

public class BaseComponent {

  @Inject
  private MyComponent component;

  @Inject
  private MyComponent qualifier;

  @Inject
  private GraphDependencyComponent dependencyComponent;

  private MyComponent componentSetterInjection;

  public MyComponent getComponentByFieldInjection() {
    return component;
  }

  @Inject
  public void setComponentBySetterBase(final MyComponent component) {
    componentSetterInjection = component;
  }

  public MyComponent getComponentBySetter() {
    return componentSetterInjection;
  }

}
