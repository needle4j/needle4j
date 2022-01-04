package org.needle4j.junit;

import org.junit.rules.MethodRule;
import org.needle4j.injection.InjectionConfiguration;
import org.needle4j.injection.InjectionProvider;

import java.util.ArrayList;
import java.util.List;

public class NeedleRuleBuilder extends AbstractNeedleRuleBuilder<NeedleRuleBuilder, NeedleRule> {
  private final List<MethodRule> methodRuleChain = new ArrayList<MethodRule>();

  public AbstractNeedleRuleBuilder<NeedleRuleBuilder, NeedleRule> withOuter(final MethodRule rule) {
    methodRuleChain.add(0, rule);
    return this;
  }

  @Override
  protected NeedleRule build(final InjectionConfiguration injectionConfiguration,
                             final InjectionProvider<?>... injectionProvider) {
    final NeedleRule needleRule = new NeedleRule(injectionConfiguration, injectionProvider);

    for (final MethodRule rule : methodRuleChain) {
      needleRule.withOuter(rule);
    }
    
    return needleRule;
  }
}
