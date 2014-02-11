package org.needle4j.injection;

import java.util.Set;

import org.needle4j.NeedleTestcase;

/**
 * <a href=
 * "http://javadocs.techempower.com/jdk18/api/java/util/function/Supplier.html"
 * >Supplies</a> a Set of InjectionProvider instances.
 * 
 * @author Jan Galinski, Holisticon AG
 */
public interface InjectionProviderInstancesSupplier {

    /**
     * <a href=
     * "http://javadocs.techempower.com/jdk18/api/java/util/function/Supplier.html"
     * >Supplies</a> a Set of InjectionProvider instances.
     * 
     * @return InjectionProviders that can be added to {@link NeedleTestcase}
     */
    Set<InjectionProvider<?>> get();
}
