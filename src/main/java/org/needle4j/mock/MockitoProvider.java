package org.needle4j.mock;

import org.mockito.Mockito;
import org.needle4j.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.needle4j.common.Preconditions.checkArgument;

/**
 * A Mockito specific {@link MockProvider} implementation. For more details, see
 * the Mockito documentation.
 */
public class MockitoProvider implements MockProvider, SpyProvider {
  static {
    // fail fast if Mockito is not available.
    try {
      Class.forName("org.mockito.Mockito");
    } catch (final ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(MockitoProvider.class);

  public static final String SPY_ANNOTATION_FQN = "org.mockito.Spy";

  private final Class<? extends Annotation> spyAnnotation = (Class<? extends Annotation>) ReflectionUtil.forName(SPY_ANNOTATION_FQN);

  /**
   * {@inheritDoc} Skipping creation, if the type is final or primitive.
   *
   * @return the mock object or null, if the type is final or primitive.
   */
  @Override
  public <T> T createMockComponent(final Class<T> type) {
    if (isFinalOrPrimitive(type)) {
      LOG.warn("Skipping creation of a mock : {} as it is final or primitive type.", type.getSimpleName());
      return null;
    }

    return Mockito.mock(type);
  }

  /**
   * {@inheritDoc} Skipping creation, if the type is final or primitive.
   *
   * @return the mock object or null, if the type is final or primitive.
   */
  @Override
  public <T> T createSpyComponent(final T instance) {
    checkArgument(instance != null, "instance must not be null!");
    if (isFinalOrPrimitive(instance.getClass())) {
      LOG.warn("Skipping creation of a spy : {} as it is final or primitive type.", instance.getClass()
          .getSimpleName());
      return null;
    }
    return Mockito.spy(instance);
  }

  /**
   * @param type
   * @return <code>true</code> if type is final or primitive,
   * <code>false</code> else.
   */
  private boolean isFinalOrPrimitive(final Class<?> type) {
    return Modifier.isFinal(type.getModifiers()) || type.isPrimitive();
  }

  @Override
  public Class<? extends Annotation> getSpyAnnotation() {
    return spyAnnotation;
  }

  @Override
  public boolean isSpyRequested(final Field field) {
    checkArgument(field != null, "field must not be null!");

    return spyAnnotation != null && field.isAnnotationPresent(spyAnnotation);
  }
}
