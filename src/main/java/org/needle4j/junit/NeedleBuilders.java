package org.needle4j.junit;

import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.needle4j.junit.testrule.DatabaseTestRule;
import org.needle4j.junit.testrule.DatabaseTestRuleBuilder;
import org.needle4j.junit.testrule.NeedleTestRule;
import org.needle4j.junit.testrule.NeedleTestRuleBuilder;
import org.needle4j.mock.MockitoProvider;

/**
 * Allows static factory method access to selected needle components.
 */
public final class NeedleBuilders {
  /**
   * @return a new builder for {@link NeedleRule}.
   */
  public static NeedleRuleBuilder needleRule() {
    return new NeedleRuleBuilder();
  }

  /**
   * @return new builder with Mockito mock provider pre configured.
   */
  public static NeedleRuleBuilder needleMockitoRule() {
    return new NeedleRuleBuilder().withMockProvider(MockitoProvider.class);
  }

  /**
   * @param configurationResourceName the configuration to read (without properties-suffix)
   * @return new builder instance
   */
  public static NeedleRuleBuilder needleRule(String configurationResourceName) {
    return new NeedleRuleBuilder().fromResource(configurationResourceName);
  }

  /**
   * @return a new builder for {@link NeedleTestRule}.
   */
  public static NeedleTestRuleBuilder needleTestRule(final Object testInstance) {
    return new NeedleTestRuleBuilder(testInstance);
  }

  /**
   * @return a new builder for {@link NeedleTestRule} with Mockito Provider
   * preconfigured.
   */
  public static NeedleTestRuleBuilder needleMockitoTestRule(final Object testInstance) {
    return new NeedleTestRuleBuilder(testInstance).withMockProvider(MockitoProvider.class);
  }

  /**
   * @return a new builder for {@link DatabaseTestRule}.
   */
  public static DatabaseTestRuleBuilder databaseTestRule() {
    return new DatabaseTestRuleBuilder();
  }

  /**
   * @return a new builder for {@link DatabaseRule}.
   */
  public static DatabaseRuleBuilder databaseRule() {
    return new DatabaseRuleBuilder();
  }

  /**
   * @param configurationResourceName config file to read (without .properties suffix)
   * @return a new builder for {@link DatabaseRule}.
   */
  public static DatabaseRuleBuilder databaseRule(String configurationResourceName) {
    return new DatabaseRuleBuilder().fromResource(configurationResourceName);
  }

  /**
   * Returns a {@code RuleChain} with a single {@link TestRule}. This method
   * is the usual starting point of a {@code RuleChain}.
   *
   * @param outerRule the outer rule of the {@code RuleChain}.
   * @return a {@code RuleChain} with a single {@link TestRule}.
   */
  public static RuleChain outerRule(final TestRule outerRule) {
    return RuleChain.outerRule(outerRule);
  }

  /**
   * Returns a {@code RuleChain} without a {@link TestRule}. This method may
   * be the starting point of a {@code RuleChain}.
   *
   * @return a {@code RuleChain} without a {@link TestRule}.
   */
  public static RuleChain emptyRuleChain() {
    return RuleChain.emptyRuleChain();
  }

  private NeedleBuilders() {
    super();
  }
}
