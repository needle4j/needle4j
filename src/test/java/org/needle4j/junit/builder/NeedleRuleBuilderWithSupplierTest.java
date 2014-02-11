package org.needle4j.junit.builder;

import static org.needle4j.junit.NeedleBuilders.needleRule;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionProviderInstancesSupplier;
import org.needle4j.injection.InjectionProviders;
import org.needle4j.junit.NeedleRule;
import org.needle4j.junit.NeedleRuleBuilder;

public class NeedleRuleBuilderWithSupplierTest {
    
    private final Runnable runnable = new RunnableImpl();
    
    @Rule
    public NeedleRule needleRule = needleRule().addAnnotation(TestBuilderQualifier.class).addSupplier(new SupplierImpl())
            .build();

    @ObjectUnderTest
    private ClassToTest objectUnderTest = new ClassToTest();

    

    @Test
    public void testInjection() throws Exception {
        Assert.assertNotNull(objectUnderTest.runnable);
        Assert.assertSame(runnable, runnable);
    }

    class ClassToTest {

        @TestBuilderQualifier
        Runnable runnable;

    }

    class RunnableImpl implements Runnable {

        @Override
        public void run() {

        }

    }

    class SupplierImpl implements InjectionProviderInstancesSupplier {

        private Set<InjectionProvider<?>> provider = new HashSet<InjectionProvider<?>>();

        public SupplierImpl() {
            provider.add(InjectionProviders.providerForQualifiedInstance(TestBuilderQualifier.class, runnable));
        }

        @Override
        public Set<InjectionProvider<?>> get() {
            return provider;
        }
    }

}
