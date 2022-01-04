package org.needle4j.junit.builder;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

import static org.needle4j.junit.NeedleBuilders.needleRule;

public class NeedleRuleBuilderWithCustomAnnotationTest {
  @SuppressWarnings("unchecked")
  @Rule
  public NeedleRule needleRule = needleRule().addAnnotation(TestBuilderQualifier.class).build();

  @ObjectUnderTest
  private ClassToTest objectUnderTest = new ClassToTest();

  @Test
  public void testInjection() {
    Assert.assertNotNull(objectUnderTest.runnable);
  }

  static class ClassToTest {
    @TestBuilderQualifier
    Runnable runnable;
  }
}
