package org.needle4j.processor;

import org.needle4j.injection.InjectionConfiguration;

import static org.needle4j.common.Preconditions.checkArgument;

public abstract class AbstractNeedleProcessor implements NeedleProcessor {

  protected InjectionConfiguration configuration;

  public AbstractNeedleProcessor(final InjectionConfiguration configuration) {
    checkArgument(configuration != null, "configuration must not be null");
    this.configuration = configuration;
  }

}
