package org.needle4j.injection.cdi.instance;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

public class InstanceFieldInjectionBean {

  @Inject
  private Instance<InstanceTestBean> instance;

  public Instance<InstanceTestBean> getInstance() {
    return instance;
  }

}
