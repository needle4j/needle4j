package org.needle4j.postconstruct.injection;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

import static org.junit.Assert.assertEquals;

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
  public void testPostConstruct_InjectIntoMany() {
    dependentComponent.count();

    // expect one call in postConstruct, one call in here
    assertEquals(dependentComponent.getCounter(), 2);
  }
}
