package org.needle4j.testcase;

import org.junit.Test;

import org.needle4j.NeedleTestcase;
import org.needle4j.ObjectUnderTestInstantiationException;
import org.needle4j.annotation.ObjectUnderTest;

@SuppressWarnings("unused")
public class ObjectUnderTestInstantiationTest extends NeedleTestcase {

    @ObjectUnderTest
    private ObjectUnderTestBean objectUnderTest;

    @Test(expected = ObjectUnderTestInstantiationException.class)
    public void testInstantiationWithNoArgConstructor() throws Exception {
        initTestcase(this);
    }

    class ObjectUnderTestBean {

        private ObjectUnderTestBean() {
            super();
        }

    }

}
