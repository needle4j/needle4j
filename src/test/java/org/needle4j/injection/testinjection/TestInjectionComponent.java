package org.needle4j.injection.testinjection;

import org.needle4j.MyEjbComponent;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
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
