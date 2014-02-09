package org.needle4j.injection.cdi.instance;

import static org.needle4j.junit.NeedleBuilders.needleRule;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import org.needle4j.annotation.Mock;
import org.needle4j.junit.NeedleRule;

@Ignore
public class InjectMockForInstanceTest {

    @Rule
    public final NeedleRule needle = needleRule().with("needle-mockito").build();

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
