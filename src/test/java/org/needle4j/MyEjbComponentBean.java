package org.needle4j;

import jakarta.inject.Inject;
import java.util.Queue;

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
