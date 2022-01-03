package org.needle4j.injection;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.MyConcreteComponent;
import org.needle4j.junit.NeedleRule;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * moved from original package to avoid
 */
public class InjectionProvidersDefaultInstanceInjectionProviderTest {

  private final MyConcreteComponent instance = new MyConcreteComponent();

  @Rule
  public final NeedleRule needle = new NeedleRule(InjectionProviders.providerForInstance(instance));

  @Inject
  private MyConcreteComponent injectedInstance;

  @Test
  public void shouldInjectInstanceA() {
    assertThat(injectedInstance, is(instance));
  }

}
