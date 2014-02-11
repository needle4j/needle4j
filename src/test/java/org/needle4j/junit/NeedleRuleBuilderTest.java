package org.needle4j.junit;

import static org.needle4j.junit.NeedleBuilders.needleRule;
import org.junit.Assert;
import org.junit.Test;

import org.needle4j.mock.EasyMockProvider;
import org.needle4j.mock.MockitoProvider;

public class NeedleRuleBuilderTest {
    
    @Test
    public void testWithMockitoProvider() throws Exception {
        NeedleRule needleRule = needleRule().withMockProvider(MockitoProvider.class).build();
        Assert.assertTrue(needleRule.getMockProvider() instanceof MockitoProvider);
    }
    
    
    @Test
    public void testWithDefaultMockProvider() throws Exception {
        NeedleRule needleRule = new NeedleRuleBuilder().build();
        Assert.assertTrue(needleRule.getMockProvider() instanceof EasyMockProvider);
    }

    @Test
    public void shouldReturnConcreteBuilderInstance() throws Exception {
        AbstractNeedleRuleBuilder<NeedleRuleBuilder, NeedleRule> builder = new NeedleRuleBuilder();
        builder.fromResource("needle").withOuter(null);
        NeedleRule rule = builder.build();

    }
}
