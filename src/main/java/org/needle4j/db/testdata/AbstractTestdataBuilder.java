package org.needle4j.db.testdata;

import org.needle4j.db.transaction.TransactionHelper;

import jakarta.persistence.EntityManager;

/**
 * An abstract implementation of {@link TestdataBuilder}.
 *
 * <pre>
 * Implementation example:
 *
 * public class PersonTestDataBuilder extends AbstractTestdataBuilder&lt;Person&gt; {
 *
 *  ...
 *
 * 	public PersonTestDataBuilder() {
 * 	 super();
 *  }
 *
 * 	public PersonTestDataBuilder(EntityManager entityManager) {
 * 	 super(entityManager);
 *  }
 *
 * 	public PersonTestDataBuilder withName(String name){
 * 	 this.withName = name;
 * 	 return this;
 *  }
 *
 * 	public PersonTestDataBuilder withAddress(Address address){
 * 	 this.withName = name;
 * 	 return this;
 *  }
 *
 * 	public Person build() {
 * 	 Person person = new Person();
 * 	 ...
 * 	 return person;
 *  }
 *
 *
 * Usage example:
 *
 * Person transientPerson = new PersonTestDataBuilder(em).build();
 * Person persistedPerson = new PersonTestDataBuilder(em).buildAndSave();
 * new PersonTestDataBuilder(em).withAddress(address).buildAndSave();
 * </pre>
 *
 * @param <T> The type of the object to build.
 *
 * @author Heinz Wilming, Alphonse Bendt, Markus Dahm Akquinet AG
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
public abstract class AbstractTestdataBuilder<T> implements TestdataBuilder<T> {
  private static int count = 0;

  private EntityManager entityManager;
  private TransactionHelper transactionHelper;

  /**
   * Creates a new {@link TestdataBuilder} with persistence.
   *
   * @param entityManager {@link EntityManager} to be used by this
   *                      {@link TestdataBuilder}
   */
  public AbstractTestdataBuilder(final EntityManager entityManager) {
    this.entityManager = entityManager;
    this.transactionHelper = new TransactionHelper(entityManager);
  }

  /**
   * Creates a new {@link TestdataBuilder} without persistence.
   */
  public AbstractTestdataBuilder() {
  }

  /**
   * Returns the EntityManager or null.
   *
   * @return {@link EntityManager} or null
   */
  protected final EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * Returns whether the {@link TestdataBuilder} is constructed with an
   * {@link EntityManager}
   *
   * @return true if {@link EntityManager} is available, else false
   */
  protected final boolean hasEntityManager() {
    return entityManager != null;
  }

  /**
   * Ensure the {@link TestdataBuilder} is constructed with an
   * {@link EntityManager}
   *
   * @throws IllegalStateException if the {@link TestdataBuilder} is constructed without an
   *                               {@link EntityManager}
   */
  protected final void ensureEntityManager() {
    if (entityManager == null) {
      throw new IllegalStateException("cannot persist w/o entity manager!");
    }
  }

  /**
   * {@inheritDoc} Executed within a new transaction.
   *
   * @throws IllegalStateException if the {@link TestdataBuilder} is constructed without an
   *                               {@link EntityManager}
   */
  @Override
  public final T buildAndSave() {
    ensureEntityManager();
    try {
      return transactionHelper.saveObject(build());
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns an integer value from a static counter.
   *
   * @return value of the static counter.
   */
  protected final int getId() {
    return count++;
  }
}
