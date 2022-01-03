package org.needle4j.injection.testinjection;

import org.needle4j.MyEjbComponent;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.net.Authenticator;

public class TestInjectionComponent {

  @Inject
  private Authenticator authenticator;

  @EJB
  private MyEjbComponent ejbComponent;

  public Authenticator getAuthenticator() {
    return authenticator;
  }

  public MyEjbComponent getEjbComponent() {
    return ejbComponent;
  }

}
