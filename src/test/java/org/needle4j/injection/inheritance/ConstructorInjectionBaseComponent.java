package org.needle4j.injection.inheritance;

import org.needle4j.MyComponent;

import javax.inject.Inject;

public class ConstructorInjectionBaseComponent {

  private MyComponent myComponent;

  @Inject
  public ConstructorInjectionBaseComponent(final MyComponent myComponent) {
    super();
    this.myComponent = myComponent;
  }

  public MyComponent getMyComponent() {
    return myComponent;
  }

}
