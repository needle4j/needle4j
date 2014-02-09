package org.needle4j.injection;

import static org.needle4j.injection.InjectionProviders.providerForQualifiedInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;

import org.needle4j.MyConcreteComponent;
import org.needle4j.junit.NeedleRule;

public class InjectionProvidersQualifiedInstanceInjectionProviderTest {



    private final MyConcreteComponent providedQualifiedInstance = new MyConcreteComponent();

    @Rule
    public final NeedleRule needle = new NeedleRule(providerForQualifiedInstance(CurrentUser.class, providedQualifiedInstance));


    @Inject
    private MyConcreteComponent mockInstance;

    @Inject
    @CurrentUser
    private MyConcreteComponent qualifiedInstance;


    @Test
    public void shouldInjectNamedInstance() {
        assertThat(qualifiedInstance, is(providedQualifiedInstance));
    }

    @Test
    public void shouldInjectDefaultInstance() {
        assertThat(mockInstance, not(providedQualifiedInstance));
    }


}
