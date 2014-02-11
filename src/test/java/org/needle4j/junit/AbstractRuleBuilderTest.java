package org.needle4j.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.needle4j.configuration.NeedleConfiguration;

public class AbstractRuleBuilderTest {

    public static class SpecializedBuilder extends AbstractRuleBuilder<SpecializedBuilder, String> {

        @Override
        protected String build(NeedleConfiguration needleConfiguration) {
            return "foo";
        }

    }

    @Test
    public void shouldReturnSpecializedBuilder() {
        assertThat(new SpecializedBuilder().fromResource("needle").getClass().getCanonicalName(),
                is(SpecializedBuilder.class.getCanonicalName()));
    }

}
