package org.needle4j.postconstruct.injection;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;
import org.needle4j.mock.EasyMockProvider;

import jakarta.inject.Inject;

@SuppressWarnings("unused")
public class PostConstructTrainMocksTest {

  @Rule
  public NeedleRule needleRule = new NeedleRule() {
    @Override
    protected void beforePostConstruct() {
      dependentComponent.count();
      EasyMock.expectLastCall().once();
      mockProvider.replayAll();
    }

  };

  @ObjectUnderTest(postConstruct = true)
  private ComponentWithPrivatePostConstruct componentWithPostConstruct;

  @Inject
  private DependentComponent dependentComponent;

  @Inject
  private EasyMockProvider mockProvider;

  @Before
  public void setup() {
    mockProvider.verifyAll();
    mockProvider.resetAll();
  }

  @Test
  public void testPostConstruct_InjectIntoMany() throws Exception {
    //
  }
}
