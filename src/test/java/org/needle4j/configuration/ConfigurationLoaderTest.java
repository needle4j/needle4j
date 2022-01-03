package org.needle4j.configuration;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.*;
import static org.needle4j.configuration.ConfigurationProperties.*;

public class ConfigurationLoaderTest {

  private final ConfigurationLoader configurationLoader = new ConfigurationLoader();

  @Test
  public void testContainsKey() throws Exception {
    assertFalse(configurationLoader.containsKey("anykey"));
    assertTrue(configurationLoader.containsKey(PERSISTENCEUNIT_NAME_KEY));
  }

  @Test
  public void testGetProperty() throws Exception {
    assertEquals("TestDataModel", configurationLoader.getProperty(PERSISTENCEUNIT_NAME_KEY));
    assertNull(configurationLoader.getProperty("any key"));
  }

  @Test
  public void canLoadCustomBundle() throws Exception {
    final Map<String, String> loadResourceAndDefault = ConfigurationLoader.loadResourceAndDefault("needle-custom");
    assertNotNull(loadResourceAndDefault);
    assertEquals("jdbc-custom", loadResourceAndDefault.get(JDBC_URL_KEY));
    assertEquals("needle-hsql-hibernate.cfg.xml", loadResourceAndDefault.get(HIBERNATE_CFG_FILENAME_KEY));
  }

  @Test
  public void defaultResourceBundleIsFetched() throws Exception {
    final Map<String, String> loadResourceAndDefault = ConfigurationLoader.loadResourceAndDefault("not-existing");
    assertNotNull(loadResourceAndDefault);
    assertEquals("needle-hsql-hibernate.cfg.xml", loadResourceAndDefault.get(HIBERNATE_CFG_FILENAME_KEY));
  }

  @Test
  public void testLoadResource() throws Exception {
    final InputStream loadResource = ConfigurationLoader.loadResource("needle.properties");
    assertNotNull(loadResource);

    final InputStream loadResourceWithLeadingSlash = ConfigurationLoader.loadResource("/needle.properties");
    assertNotNull(loadResourceWithLeadingSlash);
  }

  @Test(expected = FileNotFoundException.class)
  public void testLoadResource_NotFound() throws Exception {
    ConfigurationLoader.loadResource("notfound.properties");
  }

}
