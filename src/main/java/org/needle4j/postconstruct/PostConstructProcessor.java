package org.needle4j.postconstruct;

import org.needle4j.NeedleContext;
import org.needle4j.ObjectUnderTestInstantiationException;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.configuration.PostConstructExecuteStrategy;
import org.needle4j.processor.NeedleProcessor;
import org.needle4j.reflection.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.needle4j.configuration.PostConstructExecuteStrategy.*;

/**
 * Handles execution of postConstruction methods of instances marked with
 * {@link ObjectUnderTest#postConstruct()}
 * <p>
 * Note: Behavior in an inheritance hierarchy is not defined by the common
 * annotations specification
 * </p>
 *
 * @author Heinz Wilming, Alphonse Bendt, Markus Dahm Akquinet AG
 * @author Heinz Wilming, Alphonse Bendt, Markus Dahm Akquinet AG
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 * @author Heinz Wilming, akquinet AG (heinz.wilming@akquinet.de)
 */
public class PostConstructProcessor implements NeedleProcessor {
  /**
   * Internal Container of all Annotations that trigger invocation.
   */
  private final Set<Class<? extends Annotation>> postConstructAnnotations = new HashSet<>();
  private final PostConstructExecuteStrategy postConstructExecuteStrategy;

  public PostConstructProcessor(final Set<Class<?>> postConstructAnnotations) {
    this(postConstructAnnotations, DEFAULT);
  }

  @SuppressWarnings("unchecked")
  public PostConstructProcessor(final Set<Class<?>> postConstructAnnotations,
                                final PostConstructExecuteStrategy postConstructExecuteStrategy) {
    for (final Class<?> annotation : postConstructAnnotations) {
      this.postConstructAnnotations.add((Class<? extends Annotation>) annotation);
    }
    this.postConstructExecuteStrategy = postConstructExecuteStrategy;
  }

  /**
   * calls process(instance) for each object under test, only if field is
   * marked with {@link ObjectUnderTest}(postConstruct=true), else ignored.
   *
   * @param context the NeedleContext
   * @throws ObjectUnderTestInstantiationException
   */
  @Override
  public void process(final NeedleContext context) {
    if (this.postConstructExecuteStrategy != NEVER) {
      final Set<String> objectsUnderTestIds = context.getObjectsUnderTestIds();

      for (final String objectUnderTestId : objectsUnderTestIds) {
        final ObjectUnderTest objectUnderTestAnnotation = context.getObjectUnderTestAnnotation(objectUnderTestId);

        if (this.postConstructExecuteStrategy == ALWAYS ||
            objectUnderTestAnnotation != null && objectUnderTestAnnotation.postConstruct()) {
          try {
            process(context.getObjectUnderTest(objectUnderTestId));
          } catch (final ObjectUnderTestInstantiationException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
  }

  /**
   * invokes @PostConstruct annotated method
   *
   * @param instance
   * @throws ObjectUnderTestInstantiationException
   */
  private void process(final Object instance) throws ObjectUnderTestInstantiationException {
    final Set<Method> postConstructMethods = getPostConstructMethods(instance.getClass());

    for (final Method method : postConstructMethods) {
      try {
        ReflectionUtil.invokeMethod(method, instance);
      } catch (final Exception e) {
        throw new ObjectUnderTestInstantiationException("error executing postConstruction method '"
            + method.getName() + "'", e);
      }
    }
  }

  /**
   * @param instance
   * @return all instance methods that are marked as postConstruction methods
   */
  Set<Method> getPostConstructMethods(final Class<?> type) {
    final Set<Method> postConstructMethods = new LinkedHashSet<>();

    for (final Class<? extends Annotation> postConstructAnnotation : postConstructAnnotations) {
      postConstructMethods.addAll(ReflectionUtil.getAllMethodsWithAnnotation(type, postConstructAnnotation));
    }
    return postConstructMethods;
  }
}
