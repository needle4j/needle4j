package org.needle4j.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Used to inject an instance into multiple {@link ObjectUnderTest} instances.
 *
 * <pre>
 *  Example 1:
 *
 *  &#064;InjectIntoMany
 *  private User user = new User();
 *
 *  Example 2:
 *
 *  &#064;InjectIntoMany(value = {
 *   	InjectInto(targetComponentId = "obejctUnderTest1"),
 *   	InjectInto(targetComponentId = "obejctUnderTest2", fieldName = "user")
 *  })
 *  private User user = new User();
 * </pre>
 *
 * @author Heinz Wilming, Alphonse Bendt, Markus Dahm Akquinet AG
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface InjectIntoMany {
  /**
   * (Optional) the injection targets
   * <p>
   * Default are all {@link ObjectUnderTest} annotated fields
   */
  InjectInto[] value() default {};
}
