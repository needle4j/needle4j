package org.needle4j.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Used to inject an instance into a specific {@link ObjectUnderTest} instance.
 *
 * @author Heinz Wilming, Alphonse Bendt, Markus Dahm Akquinet AG
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface InjectInto {
  /**
   * Id of the object under test. This is the field name of the component, by
   * default. You can specify the id within the
   * <code>@{@link ObjectUnderTest}</code> annotation.
   *
   * @see ObjectUnderTest#id()
   */
  String targetComponentId();

  /**
   * (Optional) fieldName of the injection target
   *
   * <p>
   * Default is the assignable type</p>
   */
  String fieldName() default "";
}
