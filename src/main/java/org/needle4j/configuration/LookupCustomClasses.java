package org.needle4j.configuration;

import org.needle4j.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import static org.needle4j.common.Preconditions.checkArgument;

/**
 * Function to lookup classes. Expects FQN classnames separated by colon.
 */
class LookupCustomClasses {
  private static final Logger LOGGER = LoggerFactory.getLogger(LookupCustomClasses.class);

  private final Map<String, String> configurationProperties;

  public LookupCustomClasses(final Map<String, String> configurationProperties) {
    checkArgument(configurationProperties != null, "configurationProperties must not be null!");
    this.configurationProperties = configurationProperties;
  }

  public <T> Set<Class<T>> lookup(final String key) {
    final String classesList = configurationProperties.getOrDefault(key, "");
    final Set<Class<T>> result = new HashSet<>();
    final StringTokenizer tokenizer = new StringTokenizer(classesList, ",");

    while (tokenizer.hasMoreElements()) {
      try {
        final String token = tokenizer.nextToken().trim();

        if (!token.isEmpty()) {
          @SuppressWarnings("unchecked") final Class<T> clazz = (Class<T>) ReflectionUtil.forName(token);

          if (clazz != null) {
            result.add(clazz);
          } else {
            LOGGER.warn("Could not load class '{}'", token);
          }
        }
      } catch (final Exception e) {
        LOGGER.warn("Could not parse class list " + classesList, e);
      }
    }

    return result;
  }
}
