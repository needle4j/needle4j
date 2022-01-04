package org.needle4j.junit;

import org.junit.Test;
import org.needle4j.configuration.NeedleConfiguration;

import static org.junit.Assert.assertEquals;

public class AbstractRuleBuilderTest {
  public static class SpecializedBuilder extends AbstractRuleBuilder<SpecializedBuilder, String> {
    @Override
    protected String build(NeedleConfiguration needleConfiguration) {
      return "foo";
    }
  }

  @Test
  public void shouldReturnSpecializedBuilder() {
    assertEquals(new SpecializedBuilder().fromResource("needle").getClass().getCanonicalName(), SpecializedBuilder.class.getCanonicalName());
  }
}
