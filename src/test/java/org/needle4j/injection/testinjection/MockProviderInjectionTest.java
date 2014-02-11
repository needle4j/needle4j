package org.needle4j.injection.testinjection;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.junit.NeedleRule;
import org.needle4j.mock.EasyMockProvider;

public class MockProviderInjectionTest {

    @Rule
    public NeedleRule needleRule = new NeedleRule();

    @Inject
    private EasyMockProvider mockProvider;

    @Test
    public void testMockProviderInjection() throws Exception {
        Assert.assertNotNull(mockProvider);
    }

}
