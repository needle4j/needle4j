package org.needle4j.injection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleBuilders;
import org.needle4j.junit.NeedleRule;

public class ShouldNotInjectStringTest {

    public static class InjectionTargetBean {
        private String doNotInjectThis;
    }

    @Rule
    public NeedleRule needleRule = NeedleBuilders.needleMockitoRule().build();

    @ObjectUnderTest
    private InjectionTargetBean injectionTargetBean;

    @InjectIntoMany
    private String injectThis = "injectThis";

    private String doNotInjectThis;

    @Test
    public void should_not_inject_into_injectionTargetBean_doNotInjectThis() {
        Assert.assertNull(injectionTargetBean.doNotInjectThis);
    }

    @Test
    public void should_not_inject_into_testInstance_doNotInjectThis() {
        Assert.assertNull(doNotInjectThis);
    }

}
