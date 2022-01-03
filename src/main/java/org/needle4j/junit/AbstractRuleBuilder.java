package org.needle4j.junit;

import org.needle4j.common.Builder;
import org.needle4j.configuration.NeedleConfiguration;
import org.needle4j.configuration.PropertyBasedConfigurationFactory;

/**
 * @param <B> type of builder, needed for type-safe "return this"
 * @param <R> type of rule to build
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRuleBuilder<B, R> implements Builder<R> {

  protected String configurationResourceName;

  /**
   * @param configurationResourceName the config file resource to use (filename without
   *                                  ".properties" suffix)
   */
  public B fromResource(final String configurationResourceName) {
    this.configurationResourceName = configurationResourceName;
    return (B) this;
  }

  private NeedleConfiguration getNeedleConfiguration() {
    try {
      return configurationResourceName == null ? PropertyBasedConfigurationFactory.get().clone()
          : PropertyBasedConfigurationFactory.get(configurationResourceName);
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException("could not create needle configuration", e);
    }
  }

  /**
   * @return new Rule instance
   */
  public final R build() {
    return build(getNeedleConfiguration());
  }

  protected abstract R build(final NeedleConfiguration needleConfiguration);

}
