package org.needle4j.testng;

import org.needle4j.NeedleTestcase;
import org.needle4j.injection.InjectionProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import jakarta.persistence.EntityManager;

/**
 * @see NeedleTestcase
 */
public abstract class AbstractNeedleTestcase extends NeedleTestcase {
  private DatabaseTestcase databaseTestcase;

  /**
   * Creates an instance of {@link NeedleTestcase} with optional additional
   * injection provider.
   *
   * @param injectionProvider optional additional injection provider
   * @see InjectionProvider
   */
  public AbstractNeedleTestcase(InjectionProvider<?>... injectionProvider) {
    super(injectionProvider);

    for (final InjectionProvider<?> provider : injectionProvider) {
      if (provider instanceof DatabaseTestcase) {
        databaseTestcase = (DatabaseTestcase) provider;
      }
    }
  }

  @BeforeMethod
  public final void beforeNeedleTestcase() throws Exception {
    initTestcase(this);

    if (databaseTestcase != null) {
      databaseTestcase.before();
    }
  }

  @AfterMethod
  public final void afterNeedleTestcase() throws Exception {
    if (databaseTestcase != null) {
      databaseTestcase.after();
    }
  }

  /**
   * Returns the {@link EntityManager}, if the test is constructed with a
   * {@link DatabaseTestcase} instance or null otherwise.
   *
   * @return {@link EntityManager} or null
   */
  protected EntityManager getEntityManager() {
    if (databaseTestcase != null) {
      return databaseTestcase.getEntityManager();
    }

    return null;
  }
}
