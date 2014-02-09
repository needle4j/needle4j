package org.needle4j.injection;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.needle4j.NeedleContext;
import org.needle4j.annotation.InjectInto;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.processor.NeedleProcessor;
import org.needle4j.reflection.ReflectionUtil;

public class InjectionAnnotationProcessor implements NeedleProcessor {

    private final Logger logger = LoggerFactory.getLogger(InjectionAnnotationProcessor.class);

    @Override
    public void process(final NeedleContext context) {
        proccessInjectIntoMany(context);
        proccessInjectInto(context);

    }

    private void proccessInjectIntoMany(final NeedleContext context) {
        Object testcase = context.getTest();
        final List<Field> fieldsWithInjectIntoManyAnnotation = context.getAnnotatedTestcaseFields(InjectIntoMany.class);

        for (Field field : fieldsWithInjectIntoManyAnnotation) {
            final Object sourceObject = ReflectionUtil.getFieldValue(testcase, field);

            InjectIntoMany injectIntoManyAnnotation = field.getAnnotation(InjectIntoMany.class);
            InjectInto[] value = injectIntoManyAnnotation.value();

            // inject into all object under test instance
            if (value.length == 0) {
                for (Object inejctedObject : context.getObjectsUnderTest()) {
                    injectByType(inejctedObject, sourceObject, field.getType());

                }
            } else {
                for (InjectInto injectInto : value) {
                    processInjectInto(context, field, sourceObject, injectInto);
                }
            }
        }
    }

    private void proccessInjectInto(final NeedleContext context) {
        final Object testcase = context.getTest();
        final List<Field> fields = context.getAnnotatedTestcaseFields(InjectInto.class);

        for (Field field : fields) {
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
            logger.warn("could not inject component {} -  unknown object under test with id {}", sourceObject,
                    injectInto.targetComponentId());
        }
    }

    private void injectByType(final Object objectUnderTest, final Object sourceObject, final Class<?> type) {
        final List<Field> fields = ReflectionUtil.getAllFieldsAssinableFrom(type, objectUnderTest.getClass());

        for (Field field : fields) {
            try {
                ReflectionUtil.setField(field, objectUnderTest, sourceObject);
            } catch (Exception e) {
                logger.warn("could not inject into component " + objectUnderTest, e);
            }
        }

    }

    private void injectByFieldName(final Object objectUnderTest, final Object sourceObject, final String fieldName) {
        try {
            ReflectionUtil.setField(fieldName, objectUnderTest, sourceObject);
        } catch (Exception e) {
            logger.warn("could not inject into component " + objectUnderTest, e);
        }
    }

}
