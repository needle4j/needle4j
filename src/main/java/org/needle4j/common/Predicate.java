package org.needle4j.common;

/**
 * We really should include guava to avoid redundant code.
 */
public interface Predicate<T> {

  boolean apply(T input);
}
