package org.needle4j.injection;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;
import org.needle4j.mock.EasyMockProvider;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PostConstructAfterInjectionTest {

  public static class PostConstructDummyA {

    private boolean postConstructCalled;

    @PostConstruct
    public final void initDummy() {
      postConstructCalled = true;
    }

    public boolean isPostConstructCalledForDummy() {
      return postConstructCalled;
    }
  }

  public static class PostConstructDummyB {

    private boolean postConstructCalled;

    @PostConstruct
    public final void initDummy() {
      postConstructCalled = true;
    }

    public boolean isPostConstructCalledForDummy() {
      return postConstructCalled;
    }
  }

  public static class PostConstructTestObjectUnderTest extends PostConstructDummyA {

    private boolean postConstructCalled;

    @Inject
    private PostConstructDummyA dummyA;

    @Inject
    private PostConstructDummyB dummyB;

    @Inject
    public void setDummy(final PostConstructDummyA dummy) {
      this.dummyA = dummy;
    }

    public PostConstructDummyA getDummyA() {
      return this.dummyA;
    }

    public PostConstructDummyB getDummyB() {
      return this.dummyB;
    }

    @PostConstruct
    public final void initTestObjectUnderTest() {
      postConstructCalled = true;
    }

    public boolean isPostConstructCalledForTestObjectUnderTest() {
      return postConstructCalled;
    }
  }

  @Rule
  public NeedleRule needleRule = new NeedleRule();

  @ObjectUnderTest(postConstruct = true)
  private PostConstructTestObjectUnderTest objectUnderTest;

  @ObjectUnderTest(postConstruct = true)
  @InjectIntoMany
  private PostConstructDummyA dummyA;

  @Inject
  private PostConstructDummyB dummyB;

  @Inject
  private EasyMockProvider mockProvider;

  @Before
  public void initMocks() {
    dummyB = needleRule.getInjectedObject(PostConstructDummyB.class);
    EasyMock.expect(dummyB.isPostConstructCalledForDummy()).andReturn(true).anyTimes();
    mockProvider.replayAll();

    assertEquals("precondition failed", dummyA, objectUnderTest.getDummyA());
    assertEquals("precondition failed", dummyB, objectUnderTest.getDummyB());
    assertTrue("precondition failed", dummyB.isPostConstructCalledForDummy());
  }

  @Test
  public void testPostConstruct() throws Exception {
    assertTrue(dummyA.isPostConstructCalledForDummy());
    assertTrue(objectUnderTest.isPostConstructCalledForDummy());
    assertTrue(objectUnderTest.isPostConstructCalledForTestObjectUnderTest());
  }

  @Test
  public void testAssumendMockBehaviour() throws Exception {
    assertTrue(dummyB.isPostConstructCalledForDummy());
    assertTrue(objectUnderTest.getDummyB().isPostConstructCalledForDummy());
  }
}
