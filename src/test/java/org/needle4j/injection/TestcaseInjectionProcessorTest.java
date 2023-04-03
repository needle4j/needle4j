package org.needle4j.injection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.MyComponentBean;
import org.needle4j.MyEjbComponent;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;
import org.needle4j.mock.EasyMockProvider;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.SessionContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import java.util.Queue;

public class TestcaseInjectionProcessorTest {

  @Rule
  public NeedleRule needleRule = new NeedleRule();

  @ObjectUnderTest
  private MyComponentBean bean;

  @Inject
  private EasyMockProvider provider;

  @PersistenceContext
  private EntityManager entityManager;

  @Inject
  private EntityManagerFactory entityManagerFactory;

  @EJB
  private MyEjbComponent myEjbComponent;

  @Resource
  private SessionContext sessionContext;

  @Resource(mappedName = "queue1")
  private Queue queue1;

  @Resource(mappedName = "queue2")
  private Queue queue2;

  @Test
  public void testTestcaseInjection() throws Exception {
    Assert.assertNotNull(queue1);
    Assert.assertSame(queue1, bean.getQueue1());

    Assert.assertNotNull(queue2);
    Assert.assertSame(queue2, bean.getQueue2());

    Assert.assertNotNull(sessionContext);
    Assert.assertSame(sessionContext, bean.getSessionContext());

    Assert.assertNotNull(myEjbComponent);
    Assert.assertSame(myEjbComponent, bean.getMyEjbComponent());

    Assert.assertNotNull(entityManagerFactory);
    Assert.assertSame(entityManagerFactory, bean.getEntityManagerFactory());

    Assert.assertNotNull(entityManager);
    Assert.assertSame(entityManager, bean.getEntityManager());

    Assert.assertNotNull(provider);
  }

}
