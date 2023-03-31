package org.needle4j.injection;

import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

@SuppressWarnings("unused")
public class InjectionInstanceProvider {

  public static class A {

    @Inject
    private Provider<B> b;

  }

  public static interface B {

  }

  @ObjectUnderTest
  private A a;

  @Test
  public void test() {
  }

}
