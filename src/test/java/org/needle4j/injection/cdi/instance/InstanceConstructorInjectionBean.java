package org.needle4j.injection.cdi.instance;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

public class InstanceConstructorInjectionBean {

  private final Instance<InstanceTestBean> instance;

  @Inject
  public InstanceConstructorInjectionBean(final Instance<InstanceTestBean> instance) {
    this.instance = instance;
  }

  public Instance<InstanceTestBean> getInstance() {
    return instance;
  }

}
