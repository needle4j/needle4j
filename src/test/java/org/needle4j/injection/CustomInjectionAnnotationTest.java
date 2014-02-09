package org.needle4j.injection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

public class CustomInjectionAnnotationTest {

	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@ObjectUnderTest
	private CustomInjectionTestComponent component;

	@Test
	public void testCustomeInjection() throws Exception {
		Assert.assertNotNull(component.getQueue1());
		Assert.assertNotNull(component.getQueue2());
	}

}
