package org.needle4j.junit.builder;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;
import org.needle4j.junit.NeedleRuleBuilder;

public class NeedleRuleBuilderWithCustomAnnotationTest {

    @Rule
    public NeedleRule needleRule = new NeedleRuleBuilder().add(TestBuilderQualifier.class).build();

    @ObjectUnderTest
    private ClassToTest objectUnderTest = new ClassToTest();

    @Test
    public void testInjection() throws Exception {
        Assert.assertNotNull(objectUnderTest.runnable);
    }

    class ClassToTest {

        @TestBuilderQualifier
        Runnable runnable;

    }

}
