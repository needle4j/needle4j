package org.needle4j.common;

import org.junit.Test;
import org.needle4j.injection.CurrentUser;

import jakarta.inject.Named;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.needle4j.common.Annotations.isQualifier;

public class AnnotationsTest {

  @Test
  public void shouldReturnTrueForValidQualifier() {
    assertTrue(isQualifier(CurrentUser.class));
    assertTrue(isQualifier(Named.class));
  }

  @Test
  public void shouldReturnFalseIfAnnotationIsNotAQualifier() {
    assertFalse(isQualifier(Test.class));
  }

}
