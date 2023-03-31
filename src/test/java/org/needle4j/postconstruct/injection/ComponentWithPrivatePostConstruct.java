package org.needle4j.postconstruct.injection;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

public class ComponentWithPrivatePostConstruct {

  @Inject
  private DependentComponent component;

  @PostConstruct
  @SuppressWarnings("unused")
  private void postconstruct() {
    component.count();
  }

}
