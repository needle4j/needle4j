package org.needle4j.injection.inheritance;

import org.needle4j.MyComponent;

import javax.inject.Inject;

public class GraphDependencyComponent {

  @Inject
  private MyComponent component;

  public MyComponent getComponent() {
    return component;
  }

}
