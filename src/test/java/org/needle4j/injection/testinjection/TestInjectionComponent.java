package org.needle4j.injection.testinjection;

import java.net.Authenticator;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.needle4j.MyEjbComponent;

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
