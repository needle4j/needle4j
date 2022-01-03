package org.needle4j.postconstruct.injection;

public class DependentComponent {

  private int i;

  public void count() {
    i++;
  }

  public int getCounter() {
    return i;
  }
}
