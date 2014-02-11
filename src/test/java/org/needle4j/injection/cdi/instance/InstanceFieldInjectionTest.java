package org.needle4j.injection.cdi.instance;

import static org.needle4j.junit.NeedleBuilders.needleRule;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;

import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

public class InstanceFieldInjectionTest {
    
    @Rule
    public NeedleRule needleRule = needleRule("needle-mockito").build();
    
    @ObjectUnderTest
    private InstanceFieldInjectionBean component;
    
    @Inject
    private Instance<InstanceTestBean> instance;
    
    @Inject
    private Instance<Runnable> runnableInstances;
    
    @Test
    public void testInstanceFieldInjection() throws Exception {
        assertNotNull(instance);
        assertNotNull(runnableInstances);
        
        assertNotSame(instance, runnableInstances);
        assertSame(instance, component.getInstance());
    }

}
