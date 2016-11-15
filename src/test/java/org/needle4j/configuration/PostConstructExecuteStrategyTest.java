package org.needle4j.configuration;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.needle4j.configuration.PostConstructExecuteStrategy.ALWAYS;
import static org.needle4j.configuration.PostConstructExecuteStrategy.DEFAULT;
import static org.needle4j.configuration.PostConstructExecuteStrategy.NEVER;

public class PostConstructExecuteStrategyTest {

    @Test
    public void fromString() {
        assertThat(PostConstructExecuteStrategy.fromString("always"),  is(ALWAYS));
        assertThat(PostConstructExecuteStrategy.fromString("never"),   is(NEVER));
        assertThat(PostConstructExecuteStrategy.fromString("default"), is(DEFAULT));
        assertThat(PostConstructExecuteStrategy.fromString(null),      is(DEFAULT));
        assertThat(PostConstructExecuteStrategy.fromString(""),        is(DEFAULT));
        assertThat(PostConstructExecuteStrategy.fromString("foobar"),  is(DEFAULT));
    }

}