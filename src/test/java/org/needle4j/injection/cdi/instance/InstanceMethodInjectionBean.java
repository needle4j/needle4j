package org.needle4j.injection.cdi.instance;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

public class InstanceMethodInjectionBean {

  private Instance<InstanceTestBean> instance;

  public Instance<InstanceTestBean> getInstance() {
    return instance;
  }

  @Inject
  public void setInstance(final Instance<InstanceTestBean> instance) {
    this.instance = instance;
  }

}
