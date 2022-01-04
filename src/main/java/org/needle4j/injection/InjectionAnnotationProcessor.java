package org.needle4j.injection;

import org.needle4j.NeedleContext;
import org.needle4j.annotation.InjectInto;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.predicate.IsSupportedAnnotationPredicate;
import org.needle4j.processor.NeedleProcessor;
import org.needle4j.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

public class InjectionAnnotationProcessor implements NeedleProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(InjectionAnnotationProcessor.class);
  private final IsSupportedAnnotationPredicate isSupportedAnnotationPredicate;

  public InjectionAnnotationProcessor(IsSupportedAnnotationPredicate isSupportedAnnotationPredicate) {
    this.isSupportedAnnotationPredicate = isSupportedAnnotationPredicate;
  }

  @Override
  public void process(final NeedleContext context) {
    proccessInjectIntoMany(context);
    proccessInjectInto(context);
  }

  private void proccessInjectIntoMany(final NeedleContext context) {
    final Object testcase = context.getTest();
    final List<Field> fieldsWithInjectIntoManyAnnotation = context.getAnnotatedTestcaseFields(InjectIntoMany.class);

    for (final Field field : fieldsWithInjectIntoManyAnnotation) {
      final Object sourceObject = ReflectionUtil.getFieldValue(testcase, field);

      final InjectIntoMany injectIntoManyAnnotation = field.getAnnotation(InjectIntoMany.class);
      final InjectInto[] value = injectIntoManyAnnotation.value();

      // inject into all object under test instance
      if (value.length == 0) {
        for (final Object objectUnderTest : context.getObjectsUnderTest()) {
          injectByType(objectUnderTest, sourceObject, field.getType());
        }
      } else {
        for (final InjectInto injectInto : value) {
          processInjectInto(context, field, sourceObject, injectInto);
        }
      }
    }
  }

  private void proccessInjectInto(final NeedleContext context) {
    final Object testcase = context.getTest();
    final List<Field> fields = context.getAnnotatedTestcaseFields(InjectInto.class);

    for (final Field field : fields) {
      final Object sourceObject = ReflectionUtil.getFieldValue(testcase, field);
      processInjectInto(context, field, sourceObject, field.getAnnotation(InjectInto.class));
    }

  }

  private void processInjectInto(final NeedleContext context, final Field field, final Object sourceObject,
                                 final InjectInto injectInto) {
    final Object object = context.getObjectUnderTest(injectInto.targetComponentId());
    if (object != null) {

      if (injectInto.fieldName().equals("")) {
        injectByType(object, sourceObject, field.getType());
      } else {
        injectByFieldName(object, sourceObject, injectInto.fieldName());
      }

    } else {
      LOGGER.warn("could not inject component {} -  unknown object under test with id {}", sourceObject,
          injectInto.targetComponentId());
    }
  }

  private void injectByType(final Object objectUnderTest, final Object sourceObject, final Class<?> type) {
    final List<Field> fields = ReflectionUtil.getAllFieldsAssignableFrom(type, objectUnderTest.getClass());

    for (final Field field : fields) {
      // skip injection when the field is not annotated with at least one
      // supported injection annotation
      if (!isSupportedAnnotationPredicate.applyAny(field.getDeclaredAnnotations())) {
        continue;
      }
      try {
        ReflectionUtil.setField(field, objectUnderTest, sourceObject);
      } catch (final Exception e) {
        LOGGER.warn("could not inject into component " + objectUnderTest, e);
      }
    }

  }

  private void injectByFieldName(final Object objectUnderTest, final Object sourceObject, final String fieldName) {
    try {
      ReflectionUtil.setField(fieldName, objectUnderTest, sourceObject);
    } catch (final Exception e) {
      LOGGER.warn("could not inject into component " + objectUnderTest, e);
    }
  }

}
