package org.needle4j.injection.cdi.instance;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

public class InstanceConstructorInjectionTest {

  @Rule
  public NeedleRule needleRule = new NeedleRule();

  @ObjectUnderTest
  private InstanceConstructorInjectionBean component;

  @Inject
  private Instance<InstanceTestBean> instance;

  @Inject
  private Instance<Runnable> runnableInstances;

  @Test
  public void testInstanceMethodInjection() throws Exception {

    Assert.assertNotNull(instance);
    Assert.assertNotNull(runnableInstances);

    Assert.assertNotSame(instance, runnableInstances);
    Assert.assertNotNull(component.getInstance());
    Assert.assertSame(instance, component.getInstance());
  }
}
