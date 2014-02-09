package org.needle4j.injection.inheritance;

import javax.inject.Inject;

import org.needle4j.MyComponent;

public class GraphDependencyComponent {

    @Inject
    private MyComponent component;

    public MyComponent getComponent() {
        return component;
    }

}
