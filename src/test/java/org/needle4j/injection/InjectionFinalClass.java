package org.needle4j.injection;

import jakarta.inject.Inject;

public class InjectionFinalClass {

  @Inject
  private String string;

  public String getString() {
    return string;
  }
}
