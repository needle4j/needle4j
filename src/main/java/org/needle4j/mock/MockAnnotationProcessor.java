package org.needle4j.mock;

import java.lang.reflect.Field;
import java.util.List;

import org.needle4j.NeedleContext;
import org.needle4j.annotation.Mock;
import org.needle4j.injection.InjectionConfiguration;
import org.needle4j.processor.AbstractNeedleProcessor;
import org.needle4j.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockAnnotationProcessor extends AbstractNeedleProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(MockAnnotationProcessor.class);

    private final MockProvider mockProvider;

    public MockAnnotationProcessor(final InjectionConfiguration configuration) {
        super(configuration);
        mockProvider = configuration.getMockProvider();
    }

    @Override
    public void process(final NeedleContext context) {
        final List<Field> fields = context.getAnnotatedTestcaseFields(Mock.class);

        for (Field field : fields) {
            Object mock = mockProvider.createMockComponent(field.getType());

            try {
                ReflectionUtil.setField(field, context.getTest(), mock);
            } catch (Exception e) {
                LOG.warn("could not assign mock obejct " + mock, e);
            }
        }

    }

}
