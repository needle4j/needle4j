package org.needle4j.injection;

import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

public class InjectionFinalClassTest {

    @Rule
    public NeedleRule needleRule = new NeedleRule();

    @ObjectUnderTest
    private InjectionFinalClass testClass;

    @Test
    public void testFinal() throws Exception {
        assertNull(testClass.getString());

    }

}
