package org.needle4j.injection;

import org.needle4j.NeedleTestcase;

/**
 * Provides an instances of {@code T} and verifies an injection target.
 *
 * @param <T> - The type of the provided object.
 *
 *            <pre>
 *            Example for javax.inject.Qualifier:
 *
 *            public class InjectionProvider&lt;User&gt;() {
 *            	&#064;Override
 *            	public boolean verify(final InjectionTargetInformation information) {
 *            	 return information.getAnnotation(CurrentUser.class) != null;
 *              }
 *
 *            	&#064;Override
 *            	public Object getKey(final InjectionTargetInformation information) {
 *            	 return CurrentUser.class;
 *              }
 *
 *            	&#064;Override
 *            	public User getInjectedObject(final Class&lt;?&gt; type) {
 *            	 return new User();
 *              }
 *            }
 *
 *            </pre>
 *
 * @author Heinz Wilming, Alphonse Bendt, Markus Dahm Akquinet AG
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
public interface InjectionProvider<T> extends InjectionVerifier {
  /**
   * Provides an instance of {@code T}.
   *
   * @param injectionPointType the type of the injection target.
   * @return instance of {@code T}
   */
  T getInjectedObject(Class<?> injectionPointType);

  /**
   * Returns a key object, which identifies the provided object.
   *
   * @param injectionTargetInformation information about the injection point
   * @return the key of the provided object
   * @see NeedleTestcase#getInjectedObject(Object)
   */
  Object getKey(InjectionTargetInformation injectionTargetInformation);
}
