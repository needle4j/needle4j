package org.needle4j.configuration;

import org.junit.Test;
import org.needle4j.db.operation.hsql.HSQLDeleteOperation;
import org.needle4j.injection.CustomInjectionAnnotation1;
import org.needle4j.injection.CustomInjectionAnnotation2;
import org.needle4j.mock.EasyMockProvider;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PropertyBasedConfigurationFactoryTest {

  private final NeedleConfiguration needleConfiguration = PropertyBasedConfigurationFactory.get();

  @Test
  public void testGetMockProviderClass_Default() throws Exception {
    assertEquals(EasyMockProvider.class.getName(), needleConfiguration.getMockProviderClassName());
  }

  @Test
  public void testDBOperationClassName_NoDefaults() throws Exception {
    assertEquals(HSQLDeleteOperation.class.getName(), needleConfiguration.getDBOperationClassName());
  }

  @Test
  public void testGetCustomInjectionAnnotations() throws Exception {
    final Set<Class<Annotation>> customInjectionAnnotations = needleConfiguration.getCustomInjectionAnnotations();
    assertEquals(2, customInjectionAnnotations.size());
    assertTrue(customInjectionAnnotations.contains(CustomInjectionAnnotation1.class));
    assertTrue(customInjectionAnnotations.contains(CustomInjectionAnnotation2.class));
  }

  @Test
  public void testJdbcUrl() throws Exception {
    assertEquals("jdbc:hsqldb:mem:memoryDB", needleConfiguration.getJdbcUrl());
  }
}
