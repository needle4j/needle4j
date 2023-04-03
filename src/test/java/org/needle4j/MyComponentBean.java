package org.needle4j;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.SessionContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import java.util.Queue;

@SuppressWarnings("rawtypes")
public class MyComponentBean implements MyComponent {

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

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public MyEjbComponent getMyEjbComponent() {
    return myEjbComponent;
  }

  @Override
  public String testMock() {
    queue2.clear();
    return myEjbComponent.doSomething();
  }

  public EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

  public SessionContext getSessionContext() {
    return sessionContext;
  }

  public Queue getQueue1() {
    return queue1;
  }

  public Queue getQueue2() {
    return queue2;
  }

}
