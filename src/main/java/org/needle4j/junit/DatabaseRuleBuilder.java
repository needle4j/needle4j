package org.needle4j.junit;

import org.needle4j.configuration.NeedleConfiguration;

public class DatabaseRuleBuilder extends AbstractDatabaseRuleBuilder<DatabaseRuleBuilder, DatabaseRule> {
  @Override
  protected DatabaseRule createRule(final NeedleConfiguration needleConfiguration) {
    return new DatabaseRule(needleConfiguration);
  }
}
