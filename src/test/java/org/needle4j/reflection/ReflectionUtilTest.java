package org.needle4j.reflection;

import org.junit.Test;
import org.needle4j.MyComponentBean;
import org.needle4j.db.Address;
import org.needle4j.injection.InjectionTargetInformation;
import org.needle4j.mock.MockitoProvider;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ReflectionUtilTest {
  @Test
  public void testCanLookupPrivateFieldFromSuperclass() {
    final DerivedClass sample = new DerivedClass();

    final List<Field> result = ReflectionUtil.getAllFieldsWithAnnotation(sample, MyAnnotation.class);

    assertEquals(result.size(), 1);
  }

  @Test
  public void testCanInjectIntoPrivateFieldFromSuperclass() {
    final DerivedClass sample = new DerivedClass();

    ReflectionUtil.setFieldValue(sample, "aPrivateField", "aValue");

    assertEquals(sample.getPrivateField(), "aValue");
  }

  @Test
  public void testGetAllFields() {
    final List<Field> allFields = ReflectionUtil.getAllFields(DerivedClass.class);

    assertEquals(allFields.size(), 5);
  }

  @Test
  public void testAllAnnotatedFields() {
    final Map<Class<? extends Annotation>, List<Field>> allAnnotatedFields = ReflectionUtil
        .getAllAnnotatedFields(MyComponentBean.class);
    assertEquals(4, allAnnotatedFields.size());

    final List<Field> list = allAnnotatedFields.get(Resource.class);
    assertEquals(3, list.size());

  }

  @Test
  public void testInvokeMethod() throws Exception {
    final String invokeMethod = (String) ReflectionUtil.invokeMethod(this, "test");
    assertEquals("Hello World", invokeMethod);
  }

  @Test
  public void testGetFieldValue() {
    final Address address = new Address();
    address.setId(1L);

    assertEquals(1L, ReflectionUtil.getFieldValue(address, Address.class, "id"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFieldValue_Exception() {
    final Address address = new Address();

    assertEquals(1L, ReflectionUtil.getFieldValue(address, Address.class, "notexisting"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFieldValue_ByField_Exception() {
    assertEquals(1L, ReflectionUtil.getFieldValue(null, ReflectionUtil.getField(Address.class, "id")));
  }

  @Test
  public void testGetField_NoSuchField() {
    assertNull(ReflectionUtil.getField(String.class, "fieldName"));
  }

  @Test
  public void testGetField_DerivedClass() {
    assertNotNull(ReflectionUtil.getField(DerivedClass.class, "aPrivateField"));
  }

  @Test
  public void testGetMethodAndInvoke() throws Exception {
    final Method method = ReflectionUtil.getMethod(DerivedClass.class, "testGetMethod", String.class, int.class,
        Object.class);
    assertNotNull(method);

    final Object result = ReflectionUtil.invokeMethod(method, new DerivedClass(), "Hello", 1, "");
    assertEquals("Hello", result.toString());
  }

  @Test
  public void testGetMethod() {
    final List<Method> methods = ReflectionUtil.getMethods(DerivedClass.class);

    assertEquals(13, methods.size());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvokeMethod_Exception() throws Exception {
    ReflectionUtil.invokeMethod(this, "testException");
  }

  @Test
  public void testGetAllFieldsAssignableFrom() {
    final List<Field> allFieldsAssinableFromBoolean = ReflectionUtil.getAllFieldsAssignableFrom(Boolean.class, DerivedClass.class);
    assertEquals(1, allFieldsAssinableFromBoolean.size());

    final List<Field> allFieldsAssinableFromList = ReflectionUtil.getAllFieldsAssignableFrom(List.class, DerivedClass.class);
    assertEquals(2, allFieldsAssinableFromList.size());

    final List<Field> allFieldsAssinableFromCollection = ReflectionUtil.getAllFieldsAssignableFrom(Collection.class, DerivedClass.class);
    assertEquals(2, allFieldsAssinableFromCollection.size());

    final List<Field> allFieldsAssinableFromString = ReflectionUtil.getAllFieldsAssignableFrom(String.class, DerivedClass.class);
    assertEquals(2, allFieldsAssinableFromString.size());
  }

  @Test
  public void testCreateInstance() throws Exception {
    assertNotNull(ReflectionUtil.createInstance(MockitoProvider.class));

    assertEquals("Hello", ReflectionUtil.createInstance(String.class, "Hello"));
  }

  @Test(expected = Exception.class)
  public void testCreateInstance_Exception() throws Exception {
    ReflectionUtil.createInstance(InjectionTargetInformation.class);
  }

  @Test
  public void testInvokeMethod_checkArgumentsWithPrimitives() throws Exception {
    final DerivedClass derivedClass = new DerivedClass();

    final int intValue = 1;
    final float floatValue = 0F;
    final char charValue = 'c';
    final boolean booleanValue = true;
    final long longValue = 10L;
    final byte byteValue = 2;
    final short shortValue = 32;
    final double doubleValue = 24.1;

    final Object resultPrimatives = ReflectionUtil.invokeMethod(derivedClass, "testInvokeWithPrimitive", intValue,
        floatValue, charValue, booleanValue, longValue, byteValue, shortValue, doubleValue);
    assertEquals(true, resultPrimatives);

    final Object resultObjects = ReflectionUtil.invokeMethod(derivedClass, "testInvokeWithObjects", intValue,
        floatValue, charValue, booleanValue, longValue, byteValue, shortValue, doubleValue);
    assertEquals(true, resultObjects);
  }

  @Test
  public void testInvokeMethod_checkArgumentsWithObjects() throws Exception {
    final DerivedClass derivedClass = new DerivedClass();
    final Integer intValue = 1;
    final Float floatValue = 0F;
    final Character charValue = 'c';
    final Boolean booleanValue = true;
    final Long longValue = 10L;
    final Byte byteValue = 2;
    final Short shortValue = 32;
    final Double doubleValue = 24.1;

    final Object resultPrimatives = ReflectionUtil.invokeMethod(derivedClass, "testInvokeWithPrimitive", intValue,
        floatValue, charValue, booleanValue, longValue, byteValue, shortValue, doubleValue);
    assertEquals(true, resultPrimatives);

    final Object resultObjects = ReflectionUtil.invokeMethod(derivedClass, "testInvokeWithObjects", intValue,
        floatValue, charValue, booleanValue, longValue, byteValue, shortValue, doubleValue);
    assertEquals(true, resultObjects);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvokeMethod_NoSuchMethod() throws Exception {
    final DerivedClass derivedClass = new DerivedClass();

    ReflectionUtil.invokeMethod(derivedClass, "testInvokeWithPrimitive");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvokeMethod_WithWrongParameter() throws Exception {
    ReflectionUtil.invokeMethod(this, "test", 1.);
  }

  @Test
  public void shouldFindAllMethodsWithMyAnnotation() {
    final List<Method> result = ReflectionUtil.getAllMethodsWithAnnotation(DerivedClass.class, MyAnnotation.class);

    assertEquals(result.size(), 2);
  }

  @SuppressWarnings("unused")
  private String test() {
    return "Hello World";
  }

  @SuppressWarnings("unused")
  private String test(final int value) {
    return "";
  }

  @SuppressWarnings("unused")
  private void testException() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
}
