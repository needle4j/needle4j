package org.needle4j.injection.inheritance;

import javax.inject.Inject;

import org.needle4j.MyComponent;

public class ConstructorInjectionDerivedComponent extends ConstructorInjectionBaseComponent {

    private MyComponent myComponent;

    @Inject
    public ConstructorInjectionDerivedComponent(MyComponent myComponent) {
        super(myComponent);
        this.myComponent = myComponent;

    }

    public MyComponent getMyComponent() {
        return myComponent;
    }

    public MyComponent getMyComponentFromBase() {
        return super.getMyComponent();
    }

}
