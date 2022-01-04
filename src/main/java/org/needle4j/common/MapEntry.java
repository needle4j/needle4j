package org.needle4j.common;

public class MapEntry<K, V> implements java.util.Map.Entry<K, V> {
  private final K key;
  private final V value;

  public MapEntry(final K key, final V value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public K getKey() {
    return key;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
  public V setValue(final Object value) {
    throw new UnsupportedOperationException();
  }
}
