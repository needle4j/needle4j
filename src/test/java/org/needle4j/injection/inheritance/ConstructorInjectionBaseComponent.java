package org.needle4j.injection.inheritance;

import javax.inject.Inject;

import org.needle4j.MyComponent;

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
