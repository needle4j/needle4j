package org.needle4j.mock;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class MockitoProviderTest {
  private final MockitoProvider mockitoProvider = new MockitoProvider();

  @Test
  public void shouldCreateMockComponent() {
    @SuppressWarnings("unchecked") final Map<String, String> mapMock = mockitoProvider.createMockComponent(Map.class);

    final String key = "key";
    final String value = "value";

    Mockito.when(mapMock.get(key)).thenReturn(value);

    assertEquals(value, mapMock.get(key));

  }

  @Test
  public void shouldCreateSpyComponent() {
    Map<String, String> mapSpy = new HashMap<>();
    mapSpy = mockitoProvider.createSpyComponent(mapSpy);

    mapSpy.put("foo", "a");

    when(mapSpy.get("bar")).thenReturn("b");

    assertEquals(mapSpy.get("foo"), "a");
    assertEquals(mapSpy.get("bar"), "b");

    verify(mapSpy).get("foo");
    verify(mapSpy).get("bar");
    verify(mapSpy).put("foo", "a");
    verifyNoMoreInteractions(mapSpy);
  }

  @SuppressWarnings("ConstantConditions")
  @Test(expected = IllegalArgumentException.class)
  public void shouldFailToCreateSpyWhenInstanceIsNull() {
    mockitoProvider.createSpyComponent(null);
  }

  @Test
  public void shouldSkipCreateMockComponentForFinalType() {
    assertNull(mockitoProvider.createMockComponent(String.class));
  }

  @Test
  public void shouldSkipCreateMockComponentForPrimitiveType() {
    assertNull(mockitoProvider.createMockComponent(int.class));
  }

  @Test
  public void shouldSkipCreateSpyComponentForFinal() {
    assertNull(mockitoProvider.createSpyComponent("foo"));
  }

  @Test
  public void shouldSkipCreateSpyComponentForPrimitive() {
    assertNull(mockitoProvider.createSpyComponent(1));
  }
}
