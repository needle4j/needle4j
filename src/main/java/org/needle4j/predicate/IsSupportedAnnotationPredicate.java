package org.needle4j.predicate;

import org.needle4j.injection.InjectionConfiguration;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Evaluates if an annotation is supported.
 *
 * @author Jan Galinski, Holisticon AG
 */
public class IsSupportedAnnotationPredicate implements Predicate<Annotation> {
  private final InjectionConfiguration configuration;

  public IsSupportedAnnotationPredicate(InjectionConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public boolean test(final Annotation annotation) {
    return configuration.isAnnotationSupported(annotation.annotationType());
  }

  /**
   * @param annotations list of annotations
   * @return true if apply() returns true for at least one annotation
   */
  public boolean applyAny(final Annotation... annotations) {
    return Arrays.stream(annotations).anyMatch(this::test);
  }
}
