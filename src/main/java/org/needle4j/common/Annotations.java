package org.needle4j.common;

import jakarta.inject.Qualifier;
import java.lang.annotation.Annotation;

import static org.needle4j.common.Preconditions.checkArgument;

public final class Annotations {
  private Annotations() {
    // hide
  }

  /**
   * @param annotation annotation to check
   * @return <code>true</code> if annotation is marked with {@link Qualifier}.
   */
  public static boolean isQualifier(final Class<? extends Annotation> annotation) {
    checkArgument(annotation != null, "annotation must not be null");
    return annotation.getAnnotation(Qualifier.class) != null;
  }

  public static void assertIsQualifier(final Class<? extends Annotation> annotation) {
    checkArgument(isQualifier(annotation), "annotation is no qualifier");
  }
}
