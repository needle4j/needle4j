package org.needle4j.injection.inheritance;

import org.needle4j.MyComponent;

import jakarta.inject.Inject;

public class ConstructorInjectionDerivedComponent extends ConstructorInjectionBaseComponent {

  private MyComponent myComponent;

  @Inject
  public ConstructorInjectionDerivedComponent(MyComponent myComponent) {
    super(myComponent);
    this.myComponent = myComponent;

  }

  @Override
  public MyComponent getMyComponent() {
    return myComponent;
  }

  public MyComponent getMyComponentFromBase() {
    return super.getMyComponent();
  }

}
