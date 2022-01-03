package org.needle4j.predicate;

import org.needle4j.common.Predicate;
import org.needle4j.injection.InjectionConfiguration;

import java.lang.annotation.Annotation;

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
  public boolean apply(Annotation annotation) {
    return configuration.isAnnotationSupported(annotation.annotationType());
  }

  /**
   * @param annotations list of annotations
   * @return true if apply() returns true for at least one annotation
   */
  public boolean applyAny(Annotation... annotations) {
    for (final Annotation annotation : annotations) {
      if (apply(annotation)) {
        return true;
      }
    }
    return false;
  }
}
