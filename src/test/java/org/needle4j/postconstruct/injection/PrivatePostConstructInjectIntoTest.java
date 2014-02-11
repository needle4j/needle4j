package org.needle4j.postconstruct.injection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

public class PrivatePostConstructInjectIntoTest {

    @Rule
    public final NeedleRule needleRule = new NeedleRule();

    @SuppressWarnings("unused")
    @ObjectUnderTest(postConstruct = true)
    private ComponentWithPrivatePostConstruct componentWithPostConstruct;

    @InjectIntoMany
    @ObjectUnderTest
    private DependentComponent dependentComponent;

    @Test
    public void testPostConstruct_InjectIntoMany() throws Exception {
        dependentComponent.count();

        // expect one call in postConstruct, one call in here
        assertThat(dependentComponent.getCounter(), is(2));
    }
}
