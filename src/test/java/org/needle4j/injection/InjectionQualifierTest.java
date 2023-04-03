package org.needle4j.injection;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.db.User;
import org.needle4j.junit.NeedleRule;

import jakarta.inject.Inject;

import static org.junit.Assert.*;

public class InjectionQualifierTest {

  private final User currentUser = new User();

  private final InjectionProvider<User> currentUserprovider = new InjectionProvider<>() {
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

  @Inject
  @CurrentUser
  private User currentUserToInject;

  @Inject
  private User user;

  @Rule
  public NeedleRule needleRule = new NeedleRule(currentUserprovider);

  @ObjectUnderTest
  private UserDao userDao;

  @Test
  public void testInject() throws Exception {
    assertNotNull(userDao);
    assertEquals(currentUser, userDao.getCurrentUser());
    assertNotNull(userDao.getUser());
    assertNotSame(currentUser, userDao.getUser());

    assertEquals(currentUser, needleRule.getInjectedObject(CurrentUser.class));
  }

  @Test
  public void testTestInjection() throws Exception {
    assertNotNull(user);
    assertNotNull(currentUserToInject);
  }

}
