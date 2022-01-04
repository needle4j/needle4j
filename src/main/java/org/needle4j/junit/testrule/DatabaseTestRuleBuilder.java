package org.needle4j.junit.testrule;

import org.needle4j.configuration.NeedleConfiguration;
import org.needle4j.junit.AbstractDatabaseRuleBuilder;

public class DatabaseTestRuleBuilder extends AbstractDatabaseRuleBuilder<DatabaseTestRuleBuilder, DatabaseTestRule> {
  @Override
  protected DatabaseTestRule createRule(final NeedleConfiguration needleConfiguration) {
    return new DatabaseTestRule(needleConfiguration);
  }
}
