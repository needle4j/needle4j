package org.needle4j.mock;

/**
 * Interface for abstraction of a specific mock provider.
 *
 */
public interface MockProvider {

	/**
	 * Creates a mock object of a given class or interface.
	 *
	 * @param type
	 *            class or interface to mock
	 * @return mock object
	 */
	<T> T createMockComponent(Class<T> type);

}
