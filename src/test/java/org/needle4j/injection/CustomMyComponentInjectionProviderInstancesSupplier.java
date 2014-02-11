package org.needle4j.injection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.needle4j.injection.InjectionProviders.providerForInstance;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.needle4j.MyComponent;

public class CustomMyComponentInjectionProviderInstancesSupplier implements InjectionProviderInstancesSupplier {

    public static final String ID = UUID.randomUUID().toString();

    @SuppressWarnings("serial")
    @Override
    public Set<InjectionProvider<?>> get() {
        return new HashSet<InjectionProvider<?>>() {
            {
                add(providerForInstance(new MyComponent() {

                    @Override
                    public String testMock() {
                        return ID;
                    }
                }));
            }
        };
    }

    @Test
    public void shouldReturnMyFooComponent() throws Exception {
        assertThat(((MyComponent) get().iterator().next().getInjectedObject(null)).testMock(), is(ID));
    }

}
