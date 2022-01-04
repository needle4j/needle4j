package org.needle4j.injection;

public interface InjectionVerifier {
  /**
   * Verifies the injection target.
   *
   * @param injectionTargetInformation information about the injection point
   * @return true, if the provided object is injectable to the given injection
   * information, otherwise false.
   */
  boolean verify(InjectionTargetInformation injectionTargetInformation);
}
