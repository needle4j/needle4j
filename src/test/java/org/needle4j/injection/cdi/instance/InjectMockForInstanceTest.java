package org.needle4j.injection.cdi.instance;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.Mock;
import org.needle4j.junit.NeedleRule;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.needle4j.junit.NeedleBuilders.needleRule;

@Ignore
public class InjectMockForInstanceTest {

  @Rule
  public final NeedleRule needle = needleRule("needle-mockito").build();

  @Inject
  private Instance<InstanceTestBean> testBeanInstance;

  @Mock
  private InstanceTestBean testBean;

  @Test
  public void shouldInjectMockInstance() throws Exception {
    when(testBean.toString()).thenReturn("foo");

    assertThat(testBeanInstance, not(nullValue()));
    assertThat(testBeanInstance.get(), not(nullValue()));

    assertThat(testBeanInstance.toString(), is("foo"));

  }

}
