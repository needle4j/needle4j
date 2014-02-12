package org.needle4j.junit;

import static org.needle4j.injection.InjectionProviders.providersForInstancesSuppliers;
import static org.needle4j.injection.InjectionProviders.providersToArray;
import static org.needle4j.injection.InjectionProviders.providersToSet;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.needle4j.NeedleTestcase;
import org.needle4j.configuration.NeedleConfiguration;
import org.needle4j.injection.InjectionConfiguration;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionProviderInstancesSupplier;
import org.needle4j.mock.MockProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @param <B>
 *            type of builder, needed for type-safe "return this"
 * @param <R>
 *            type of rule to build
 */
@SuppressWarnings("unchecked")
public abstract class AbstractNeedleRuleBuilder<B, R extends NeedleTestcase> extends AbstractRuleBuilder<B, R> {

    private final Logger logger = LoggerFactory.getLogger(AbstractNeedleRuleBuilder.class);

    private Class<? extends MockProvider> mockProviderClass;
    private Class<?>[] withAnnotations = {};
    private final Set<InjectionProvider<?>> providers = new HashSet<InjectionProvider<?>>();

    public B withMockProvider(final Class<? extends MockProvider> mockProviderClass) {
        this.mockProviderClass = mockProviderClass;
        return (B) this;
    }

    public B addProvider(final InjectionProvider<?>... injectionProviders) {
        providers.addAll(providersToSet(injectionProviders));
        return (B) this;
    }

    public B addAnnotation(final Class<? extends Annotation>... annotations) {
        this.withAnnotations = annotations;
        return (B) this;
    }

    public B addSupplier(final InjectionProviderInstancesSupplier... suppliers) {
        this.providers.addAll(providersToSet(providersForInstancesSuppliers(suppliers)));
        return (B) this;
    }

    private Class<? extends MockProvider> getMockProviderClass(final NeedleConfiguration needleConfiguration) {
        return mockProviderClass != null ? mockProviderClass : InjectionConfiguration
                .lookupMockProviderClass(needleConfiguration.getMockProviderClassName());
    }

    private Set<Class<Annotation>> getCustomInjectionAnnotations() {
        final Set<Class<Annotation>> annotations = new HashSet<Class<Annotation>>();
        for (final Class<?> annotationClass : withAnnotations) {
            if (annotationClass.isAnnotation()) {
                annotations.add((Class<Annotation>) annotationClass);
            } else {
                logger.warn("ignore class {}", annotationClass);
            }
        }

        return annotations;
    }


    @Override
    protected final R build(final NeedleConfiguration needleConfiguration) {
        if (this.mockProviderClass != null) {
          needleConfiguration.setMockProviderClassName(this.mockProviderClass.getCanonicalName());
        }

        final InjectionConfiguration injectionConfiguration = new InjectionConfiguration(needleConfiguration);

        injectionConfiguration.addGlobalInjectionAnnotation(getCustomInjectionAnnotations());

        return build(injectionConfiguration, providersToArray(providers));
    }

    protected abstract R build(final InjectionConfiguration injectionConfiguration,
            final InjectionProvider<?>... injectionProvider);

}
