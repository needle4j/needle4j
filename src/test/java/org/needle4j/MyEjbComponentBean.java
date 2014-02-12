package org.needle4j;

import java.util.Queue;

import javax.inject.Inject;

public class MyEjbComponentBean implements MyEjbComponent {

    @Inject
    private String testInjection;

    private Queue<?> queue;

    @Override
    public String doSomething() {
        return "Hello World";
    }

    public String getTestInjection() {
        return testInjection;
    }

    public Queue<?> getQueue() {
        return queue;
    }

}
