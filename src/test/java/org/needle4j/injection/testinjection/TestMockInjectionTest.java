package org.needle4j.injection.testinjection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.MyEjbComponent;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import java.net.Authenticator;

public class TestMockInjectionTest {

  @Rule
  public NeedleRule needleRule = new NeedleRule();

  @ObjectUnderTest
  private TestInjectionComponent objectUnderTest;

  @Inject
  private Authenticator authenticator;

  @EJB
  private MyEjbComponent ejbComponent;

  @Test
  public void testInjection() throws Exception {
    Assert.assertNotNull(authenticator);
    Assert.assertEquals(objectUnderTest.getAuthenticator(), authenticator);

    Assert.assertNotNull(ejbComponent);
    Assert.assertEquals(objectUnderTest.getEjbComponent(), ejbComponent);
  }

}
