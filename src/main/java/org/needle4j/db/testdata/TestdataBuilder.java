package org.needle4j.db.testdata;

/**
 * Interface for a concrete TestDataBuilder implementation.
 * 
 * @param <T>
 *            the type to build
 */
public interface TestdataBuilder<T> {

    /**
     * Creates a new instance of generic type.
     *
     * @return a new instance of generic type
     */
    T build();

    /**
     * Creates a new instance of generic type  and saves the instance.
     * 
     * @return a new persisted instance of generic type
     */
    T buildAndSave();

}
