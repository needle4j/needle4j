package org.needle4j.common;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.needle4j.common.Preconditions.checkState;

public class PreconditionsTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldThrowIllegalStateException() {
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("failed with param=foo");

    checkState(false, "failed with param=%s", "foo");
  }

  @Test
  public void shouldPassWithoutException() {
    checkState(true, "should pass");
    // success if no exception thrown
  }

}
