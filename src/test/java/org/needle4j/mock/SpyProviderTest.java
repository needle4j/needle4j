package org.needle4j.mock;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Spy;
import org.needle4j.annotation.InjectInto;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

import javax.inject.Inject;
import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.needle4j.junit.NeedleBuilders.needleRule;

public class SpyProviderTest {

  public static interface B {

    String getName();
  }

  public static class BImpl implements B {

    @Override
    public String getName() {
      return "foo";
    }
  }

  public static class A {

    @Inject
    private B b;

    public String hello() {
      return "hello " + b.getName();
    }
  }

  public static class C {

    public String getName() {
      return "c";
    }

  }

  @Rule
  public final NeedleRule needle = needleRule("needle-mockito").build();

  @ObjectUnderTest
  @Spy
  private A a;

  @ObjectUnderTest(implementation = BImpl.class)
  @InjectInto(targetComponentId = "a")
  @Spy
  private B b;

  @Test
  public void shouldInjectSpyForA() {
    when(b.getName()).thenReturn("world");

    assertThat(a.hello(), is("hello world"));
    verify(a).hello();
    verify(b).getName();
  }

  @Test
  public void shouldNotRequestSpyWhenAnnotationIsNull() throws Exception {
    Field field = SpyProviderTest.class.getDeclaredField("b");
    assertFalse(SpyProvider.FAKE.isSpyRequested(field));
  }

}
