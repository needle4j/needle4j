package org.needle4j.injection;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.needle4j.mock.EasyMockProvider;
import org.needle4j.mock.MockProvider;
import org.needle4j.mock.MockitoProvider;

public class InjectionConfigurationTest {

	
	@Test
	public void testCreateMockProvider() throws Exception {
	    Class<? extends MockProvider> lookupMockProviderClass = EasyMockProvider.class;
	    InjectionConfiguration injectionConfiguration = new InjectionConfiguration();
		MockProvider mockProvider = injectionConfiguration.createMockProvider(lookupMockProviderClass);
		assertTrue(mockProvider instanceof EasyMockProvider);
	}
	
	@Test
    public void testLookupMockProviderClass() throws Exception {
        assertNotNull(InjectionConfiguration.lookupMockProviderClass(MockitoProvider.class.getName()));
    }

    
    @Test(expected = RuntimeException.class)
    public void testLookupMockProviderClass_Null() throws Exception {
        assertNull(InjectionConfiguration.lookupMockProviderClass(null));
    }

}
