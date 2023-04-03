package org.needle4j.testng;

import org.needle4j.MyComponentBean;
import org.needle4j.MyEjbComponent;
import org.needle4j.MyEjbComponentBean;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.annotation.ObjectUnderTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import jakarta.ejb.SessionContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class NeedleTestNGTest extends AbstractNeedleTestcase {

  public NeedleTestNGTest() {
    super(new DatabaseTestcase("TestDataModel"));
  }

  @ObjectUnderTest
  private MyComponentBean componentBean;

  @InjectIntoMany
  @ObjectUnderTest(implementation = MyEjbComponentBean.class)
  private MyEjbComponent ejbComponent;

  private MyComponentBean componentBean1 = new MyComponentBean();

  @ObjectUnderTest
  private MyComponentBean componentBean2 = componentBean1;

  @Inject
  private EntityManager entityManager;

  @Inject
  private EntityTransaction entityTransaction;

  @Test
  public void testBasicInjection() throws Exception {
    Assert.assertNotNull(componentBean);
    Assert.assertNotNull(componentBean.getEntityManager());
    Assert.assertNotNull(componentBean.getMyEjbComponent());

    final MyEjbComponent mock = (MyEjbComponent) getInjectedObject(MyEjbComponent.class);

    Assert.assertNotNull(mock);
  }

  @Test
  public void testResourceMock() throws Exception {
    final SessionContext sessionContextMock = (SessionContext) getInjectedObject(SessionContext.class);
    Assert.assertNotNull(sessionContextMock);

    Assert.assertNotNull(getInjectedObject("queue1"));
    Assert.assertNotNull(getInjectedObject("queue2"));
  }

  @Test
  public void testInjectInto() throws Exception {
    Assert.assertNotNull(ejbComponent);
    Assert.assertEquals(ejbComponent, componentBean.getMyEjbComponent());
  }

  @Test
  public void testInitInstance() throws Exception {
    Assert.assertEquals(componentBean1, componentBean2);
  }

  @Test
  public void testGetEntityManager() throws Exception {
    final EntityManager entityManager = getEntityManager();
    Assert.assertNotNull(entityManager);
  }

  @Test
  public void testEntityManagerInjection() throws Exception {
    Assert.assertNotNull(entityManager);
    final EntityManager entityManager = getEntityManager();
    Assert.assertSame(this.entityManager, entityManager);
  }

  @Test
  public void testEntityTransactionInjection() throws Exception {
    Assert.assertNotNull(entityTransaction);
  }

}
