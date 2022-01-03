package org.needle4j.injection.constuctor;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.db.User;
import org.needle4j.injection.CurrentUser;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionTargetInformation;
import org.needle4j.injection.method.UserDao;
import org.needle4j.junit.NeedleRule;

public class ConstructorInjectionTest {

  private final User currentUser = new User();

  private final InjectionProvider<User> currentUserprovider = new InjectionProvider<User>() {
    @Override
    public boolean verify(final InjectionTargetInformation information) {
      return information.getAnnotation(CurrentUser.class) != null;
    }

    @Override
    public Object getKey(final InjectionTargetInformation information) {
      return CurrentUser.class;
    }

    @Override
    public User getInjectedObject(final Class<?> type) {
      return currentUser;
    }
  };

  @Rule
  public NeedleRule needleRule = new NeedleRule(currentUserprovider);

  @ObjectUnderTest
  private UserDao userDao;

  @Test
  public void testSetterInjection() throws Exception {
    Assert.assertNotNull(userDao.getUser());
  }

  @Test
  public void testSetterInjection_Qualifyer() throws Exception {
    User currentUser2 = userDao.getCurrentUser();

    Assert.assertSame(currentUser, currentUser2);
  }

  @Test
  public void testMultipleMethodInjection() throws Exception {
    Assert.assertNotNull(userDao.getQueue1());
    Assert.assertNotNull(userDao.getQueue2());
  }

}
