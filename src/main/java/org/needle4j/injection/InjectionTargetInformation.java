package org.needle4j.injection;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Provides information about the injection target.
 */
public class InjectionTargetInformation {
  private final Class<?> type;
  private final AccessibleObject accessibleObject;
  private final Annotation[] parameterAnnotations;
  private final Type genericTypeParameter;

  private InjectionTargetInformation(final Class<?> type, final AccessibleObject accessibleObject,
                                     final Type genericTypeParameter, final Annotation... parameterAnnotations) {
    this.type = type;
    this.accessibleObject = accessibleObject;
    this.parameterAnnotations = parameterAnnotations;
    this.genericTypeParameter = genericTypeParameter;
  }

  /**
   * Creates an instance of {@link InjectionTargetInformation} for
   * {@link Field} injection.
   *
   * @param type  class object of the injection target
   * @param field {@link Field} object of the injection target
   */
  public InjectionTargetInformation(final Class<?> type, final Field field) {
    this(type, field, field != null ? field.getGenericType() : null);
  }

  /**
   * Creates an instance of {@link InjectionTargetInformation} for
   * {@link Method} injection.
   *
   * @param type                 class object of the method parameter
   * @param method               {@link Method} object of the injection target
   * @param parameterAnnotations annotations of method parameter
   */
  public InjectionTargetInformation(final Class<?> type, final Method method, final Type genericTypeParamerter,
                                    final Annotation[] parameterAnnotations) {
    this(type, (AccessibleObject) method, genericTypeParamerter, parameterAnnotations);
  }

  /**
   * Creates an instance of {@link InjectionTargetInformation} for
   * {@link Constructor} injection.
   *
   * @param type                 class object of the constructor parameter
   * @param constructor          {@link Constructor} object of the injection target
   * @param parameterAnnotations annotations of constructor parameter
   */
  public InjectionTargetInformation(final Class<?> type, final Constructor<?> constructor,
                                    final Type genericTypeParamerter, final Annotation[] parameterAnnotations) {
    this(type, (AccessibleObject) constructor, genericTypeParamerter, parameterAnnotations);
  }

  /**
   * Returns the class object from the injection target.
   *
   * @return type of the injection target
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Returns an array of all annotations present on the injection target.
   * <p>
   * If the {@link AccessibleObject} of the injection target is of type
   * {@link Method} or {@link Constructor}, then the {@link Annotation} of the
   * {@link AccessibleObject} and the corresponding parameter are returned.
   *
   * @return Array of all annotations present on the injection target
   */
  public Annotation[] getAnnotations() {
    final Annotation[] accessibleObjectAnnotations = accessibleObject.getAnnotations();
    final Annotation[] annotations = new Annotation[accessibleObjectAnnotations.length
        + parameterAnnotations.length];

    System.arraycopy(accessibleObjectAnnotations, 0, annotations, 0, accessibleObjectAnnotations.length);
    System.arraycopy(parameterAnnotations, 0, annotations, accessibleObjectAnnotations.length,
        parameterAnnotations.length);

    return annotations;
  }

  /**
   * Returns the {@link AccessibleObject} of the injection target.
   *
   * @return the {@link AccessibleObject} of the injection target
   * @see Field
   * @see Method
   * @see Constructor
   */
  public AccessibleObject getAccessibleObject() {
    return accessibleObject;
  }

  /**
   * Returns true if an annotation for the specified type is present on the
   * injection target, else false.
   *
   * @param annotationClass - the Class object corresponding to the annotation type
   * @return true if an annotation for the specified annotation type is
   * present on this element, else false
   * @throws NullPointerException - if the given annotation class is null
   */
  public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
    return getAnnotation(annotationClass) != null;
  }

  /**
   * Returns the {@link Annotation} object if an annotation for the specified
   * type is present on the injection target, otherwise null.
   * <p>
   * If the {@link AccessibleObject} of the injection target is of type
   * {@link Method} or {@link Constructor}, then the {@link Annotation} may be
   * specified on the {@link AccessibleObject} or on the corresponding
   * parameter.
   *
   * @param annotationClass - the Class object corresponding to the annotation type
   * @return annotation for the specified annotation type if present on this
   * element, otherwise null
   * @throws NullPointerException - if the given annotation class is null
   */
  @SuppressWarnings("unchecked")
  public <T> T getAnnotation(final Class<? extends Annotation> annotationClass) {
    final Annotation annotation = accessibleObject.getAnnotation(annotationClass);
    if (annotation != null) {
      return (T) annotation;
    }

    for (final Annotation parameterAnnotation : parameterAnnotations) {
      if (parameterAnnotation.annotationType() == annotationClass) {
        return (T) parameterAnnotation;
      }
    }

    return null;
  }

  public Type getGenericTypeParameter() {
    return genericTypeParameter;
  }
}
