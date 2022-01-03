package org.needle4j;

import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.reflection.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class NeedleContext {
  private final Object test;
  private final Map<String, Object> objectsUnderTest = new HashMap<>();
  private final Map<String, ObjectUnderTest> objectUnderTestAnnotations = new HashMap<>();
  private final Map<Object, Object> injectedObjects = new HashMap<>();

  private final Map<Class<? extends Annotation>, List<Field>> annotatedTestcaseFieldMap;

  public NeedleContext(final Object test) {
    this.test = test;
    annotatedTestcaseFieldMap = ReflectionUtil.getAllAnnotatedFields(test.getClass());
  }

  public Object getTest() {
    return test;
  }

  @SuppressWarnings("unchecked")
  public <X> X getInjectedObject(final Object key) {
    return (X) injectedObjects.get(key);
  }

  public Collection<Object> getInjectedObjects() {
    return injectedObjects.values();
  }

  public void addInjectedObject(final Object key, final Object instance) {
    injectedObjects.put(key, instance);
  }

  public Object getObjectUnderTest(final String id) {
    return objectsUnderTest.get(id);
  }

  public ObjectUnderTest getObjectUnderTestAnnotation(final String id) {
    return objectUnderTestAnnotations.get(id);
  }

  public void addObjectUnderTest(final String id, final Object instance,
                                 final ObjectUnderTest objectUnderTestAnnotation) {
    objectsUnderTest.put(id, instance);
    objectUnderTestAnnotations.put(id, objectUnderTestAnnotation);
  }

  public Collection<Object> getObjectsUnderTest() {
    return objectsUnderTest.values();
  }

  public Set<String> getObjectsUnderTestIds() {
    return objectsUnderTest.keySet();
  }

  public List<Field> getAnnotatedTestcaseFields(final Class<? extends Annotation> annotationClass) {
    final List<Field> value = annotatedTestcaseFieldMap.get(annotationClass);
    return value != null ? value : new ArrayList<>();
  }

}
