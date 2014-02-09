package org.needle4j.mock;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.needle4j.annotation.Mock;
import org.needle4j.junit.NeedleRule;

public class MockAnnotationTest {

	@Rule
	public NeedleRule needleRule = new NeedleRule();

	@Mock
	private EntityManager entityManagerMock;


	@Test
	public void testMockAnnotation() throws Exception {
		Assert.assertNotNull(entityManagerMock);
    }
}
