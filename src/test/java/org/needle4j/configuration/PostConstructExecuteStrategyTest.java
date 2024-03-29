package org.needle4j.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.needle4j.configuration.PostConstructExecuteStrategy.*;

@RunWith(Parameterized.class)
public class PostConstructExecuteStrategyTest {
  private final String value;
  private final PostConstructExecuteStrategy expected;

  public PostConstructExecuteStrategyTest(final String value, final PostConstructExecuteStrategy expected) {
    this.value = value;
    this.expected = expected;
  }

  @Parameterized.Parameters(name = "{index}: fromString({0}) == {1}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"always", ALWAYS},
        {"AlwAys", ALWAYS},
        {"never", NEVER},
        {"nEvEr", NEVER},
        {"default", DEFAULT},
        {"dEfAult", DEFAULT},
        {"foobar", DEFAULT},
        {"", DEFAULT},
        {null, DEFAULT}
    });
  }

  @Test
  public void fromString() {
    assertEquals(PostConstructExecuteStrategy.fromString(this.value), this.expected);
  }
}