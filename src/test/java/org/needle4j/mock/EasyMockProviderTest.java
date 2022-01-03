package org.needle4j.mock;

import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.MyComponent;
import org.needle4j.MyComponentBean;
import org.needle4j.MyEjbComponent;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.db.User;
import org.needle4j.injection.constuctor.UserDao;
import org.needle4j.junit.NeedleRule;

import javax.inject.Inject;

import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class EasyMockProviderTest {
  @Rule
  public final NeedleRule needleRule = new NeedleRule();

  @ObjectUnderTest(implementation = MyComponentBean.class)
  private MyComponent component;

  @Inject
  private EasyMockProvider mockProvider;

  @Test
  public void testNiceMock() {
    final String testMock = component.testMock();
    assertNull(testMock);
  }

  @Test
  public void testStrictMock() {
    final MyEjbComponent myEjbComponentMock = needleRule.getInjectedObject(MyEjbComponent.class);
    assertNotNull(myEjbComponentMock);

    EasyMock.resetToStrict(myEjbComponentMock);

    EasyMock.expect(myEjbComponentMock.doSomething()).andReturn("Hello World");

    mockProvider.replayAll();
    final String testMock = component.testMock();

    assertEquals("Hello World", testMock);

    mockProvider.verifyAll();
  }

  @Test(expected = IllegalStateException.class)
  public void testResetToStrict_Mocks() {
    final User userMock = mockProvider.createMock(User.class);
    final UserDao userDaoMock = mockProvider.createMock(UserDao.class);
    mockProvider.resetToStrict(userMock, userDaoMock);
    userMock.getId();

    userDaoMock.getUser();

    mockProvider.replayAll();

    mockProvider.verifyAll();
  }

  @Test(expected = IllegalStateException.class)
  public void testResetToStrict_Mock() {
    final User userMock = mockProvider.createMock(User.class);
    mockProvider.resetToStrict(userMock);

    userMock.getId();

    mockProvider.replayAll();

    mockProvider.verifyAll();
  }
}
