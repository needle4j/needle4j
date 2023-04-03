package org.needle4j.injection;

import org.needle4j.common.MapEntry;
import org.needle4j.configuration.NeedleConfiguration;
import org.needle4j.configuration.PropertyBasedConfigurationFactory;
import org.needle4j.mock.MockAnnotationProcessor;
import org.needle4j.mock.MockProvider;
import org.needle4j.mock.SpyProvider;
import org.needle4j.postconstruct.PostConstructProcessor;
import org.needle4j.predicate.IsSupportedAnnotationPredicate;
import org.needle4j.processor.ChainedNeedleProcessor;
import org.needle4j.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.Map.Entry;

import static org.needle4j.reflection.ReflectionUtil.forName;

@SuppressWarnings("unused")
public final class InjectionConfiguration {
  private static final Logger LOG = LoggerFactory.getLogger(InjectionConfiguration.class);

  private static final Set<Class<?>> POSTCONSTRUCT_CLASSES = ReflectionUtil
      .getClasses("jakarta.annotation.PostConstruct");

  private static final Class<?> RESOURCE_CLASS = forName("jakarta.annotation.Resource");
  private static final Class<?> INJECT_CLASS = forName("jakarta.inject.Inject");
  private static final Class<?> CDI_INSTANCE_CLASS = forName("jakarta.enterprise.inject.Instance");
  private static final Class<?> EJB_CLASS = forName("jakarta.ejb.EJB");
  private static final Class<?> PERSISTENCE_CONTEXT_CLASS = forName("jakarta.persistence.PersistenceContext");
  private static final Class<?> PERSISTENCE_UNIT_CLASS = forName("jakarta.persistence.PersistenceUnit");

  private final NeedleConfiguration needleConfiguration;

  // Default InjectionProvider for annotations
  private final List<InjectionProvider<?>> injectionProviderList = new ArrayList<>();

  // Global InjectionProvider for custom implementation
  private final List<InjectionProvider<?>> globalInjectionProviderList = new ArrayList<>();

  // Test-specific custom injection provider
  private final List<InjectionProvider<?>> testInjectionProvider = new ArrayList<>();

  // all with priority order
  private final List<List<InjectionProvider<?>>> allInjectionProvider;

  private final Set<Class<? extends Annotation>> injectionAnnotationClasses = new HashSet<>();

  private final MockProvider mockProvider;
  private final SpyProvider spyProvider;

  private final PostConstructProcessor postConstructProcessor;
  private final InjectionAnnotationProcessor injectionIntoAnnotationProcessor;
  private final TestcaseInjectionProcessor testcaseInjectionProcessor;
  private final MockAnnotationProcessor mockAnnotationProcessor;
  private final ChainedNeedleProcessor chainedNeedleProcessor;

  /**
   * @see #InjectionConfiguration(NeedleConfiguration)
   */
  public InjectionConfiguration() {
    this(PropertyBasedConfigurationFactory.get());
  }

  public InjectionConfiguration(final NeedleConfiguration needleConfiguration) {
    this.needleConfiguration = needleConfiguration;
    this.mockProvider = createMockProvider(lookupMockProviderClass(needleConfiguration.getMockProviderClassName()));

    // use mockprovider if mockprovider supports spies, otherwise Fake
    // implementation
    this.spyProvider = (this.mockProvider instanceof SpyProvider) ? (SpyProvider) mockProvider : SpyProvider.FAKE;

    this.postConstructProcessor = new PostConstructProcessor(
        POSTCONSTRUCT_CLASSES, needleConfiguration.getPostConstructExecuteStrategy());

    this.injectionIntoAnnotationProcessor = new InjectionAnnotationProcessor(new IsSupportedAnnotationPredicate(
        this));
    this.testcaseInjectionProcessor = new TestcaseInjectionProcessor(this);
    this.mockAnnotationProcessor = new MockAnnotationProcessor(this);

    this.chainedNeedleProcessor = new ChainedNeedleProcessor(mockAnnotationProcessor,
        injectionIntoAnnotationProcessor, testcaseInjectionProcessor);

    addCdiInstance();
    add(INJECT_CLASS);
    add(EJB_CLASS);
    add(PERSISTENCE_CONTEXT_CLASS);
    add(PERSISTENCE_UNIT_CLASS);
    addResource();

    initGlobalInjectionAnnotation();
    initGlobalInjectionProvider();

    injectionProviderList.add(0, new MockProviderInjectionProvider(mockProvider));

    allInjectionProvider = Arrays.asList(testInjectionProvider, globalInjectionProviderList, injectionProviderList);

  }

  private void addResource() {

    if (RESOURCE_CLASS != null) {
      addInjectionAnnotation(RESOURCE_CLASS);
      injectionProviderList.add(new ResourceMockInjectionProvider(this));
    }
  }

  private void addCdiInstance() {

    if (CDI_INSTANCE_CLASS != null) {
      // addInjectionAnnotation(RESOURCE_CLASS);
      injectionProviderList.add(new CDIInstanceInjectionProvider(CDI_INSTANCE_CLASS, this));
    }
  }

  private void add(final Class<?> clazz) {

    if (clazz != null) {
      LOG.debug("register injection handler for class {}", clazz);
      injectionProviderList.add(new DefaultMockInjectionProvider(clazz, this));
      addInjectionAnnotation(clazz);
    }

  }

  @SuppressWarnings("unchecked")
  public <T extends MockProvider> T getMockProvider() {
    return (T) mockProvider;
  }

  public SpyProvider getSpyProvider() {
    return spyProvider;
  }

  public PostConstructProcessor getPostConstructProcessor() {
    return postConstructProcessor;
  }

  public InjectionAnnotationProcessor getInjectionIntoAnnotationProcessor() {
    return injectionIntoAnnotationProcessor;
  }

  public TestcaseInjectionProcessor getTestcaseInjectionProcessor() {
    return testcaseInjectionProcessor;
  }

  public MockAnnotationProcessor getMockAnnotationProcessor() {
    return mockAnnotationProcessor;
  }

  public ChainedNeedleProcessor getChainedNeedleProcessor() {
    return chainedNeedleProcessor;
  }

  public void addInjectionProvider(final InjectionProvider<?>... injectionProvider) {
    for (final InjectionProvider<?> provider : injectionProvider) {
      testInjectionProvider.add(0, provider);
    }
  }

  public List<List<InjectionProvider<?>>> getInjectionProvider() {
    return allInjectionProvider;
  }

  private void initGlobalInjectionAnnotation() {
    final Set<Class<Annotation>> customInjectionAnnotations = needleConfiguration.getCustomInjectionAnnotations();
    addGlobalInjectionAnnotation(customInjectionAnnotations);
  }

  public void addGlobalInjectionAnnotation(final Set<Class<Annotation>> customInjectionAnnotations) {
    for (final Class<? extends Annotation> annotation : customInjectionAnnotations) {
      addInjectionAnnotation(annotation);
      globalInjectionProviderList.add(0, new DefaultMockInjectionProvider(annotation, this));
    }
  }

  private void initGlobalInjectionProvider() {

    final Set<Class<InjectionProvider<?>>> customInjectionProviders = needleConfiguration
        .getCustomInjectionProviderClasses();
    for (final Class<InjectionProvider<?>> injectionProviderClass : customInjectionProviders) {
      try {
        final InjectionProvider<?> injection = ReflectionUtil.createInstance(injectionProviderClass);
        globalInjectionProviderList.add(0, injection);
      } catch (final Exception e) {
        LOG.warn("could not create an instance of injection provider " + injectionProviderClass, e);
      }
    }

    for (final Class<InjectionProviderInstancesSupplier> supplierClass : needleConfiguration
        .getCustomInjectionProviderInstancesSupplierClasses()) {
      try {
        final InjectionProviderInstancesSupplier supplier = ReflectionUtil.createInstance(supplierClass);
        globalInjectionProviderList.addAll(0, supplier.get());
      } catch (final Exception e) {
        LOG.warn("could not create an instance of injection provider instance supplier " + supplierClass, e);
      }

    }

  }

  @SuppressWarnings("unchecked")
  private void addInjectionAnnotation(final Class<?> clazz) {
    if (clazz.isAnnotation()) {
      injectionAnnotationClasses.add((Class<? extends Annotation>) clazz);
    }
  }

  public boolean isAnnotationSupported(final Class<? extends Annotation> annotation) {
    return injectionAnnotationClasses.contains(annotation);
  }

  Set<Class<? extends Annotation>> getSupportedAnnotations() {
    return Collections.unmodifiableSet(injectionAnnotationClasses);
  }

  public Entry<Object, Object> handleInjectionProvider(final Collection<InjectionProvider<?>> injectionProviders,
                                                       final InjectionTargetInformation injectionTargetInformation) {

    for (final InjectionProvider<?> provider : injectionProviders) {

      if (provider.verify(injectionTargetInformation)) {
        final Object object = provider.getInjectedObject(injectionTargetInformation.getType());
        final Object key = provider.getKey(injectionTargetInformation);

        return new MapEntry<>(key, object);
      }
    }
    return null;
  }

  public static Class<? extends MockProvider> lookupMockProviderClass(final String mockProviderClassName) {
    if (mockProviderClassName == null) {
      return autoDetectMockProviderClass();
    } else {
      try {
        return ReflectionUtil.lookupClass(MockProvider.class, mockProviderClassName);
      } catch (final Exception e) {
        throw new RuntimeException("could not load mock provider class: '" + mockProviderClassName + "'", e);
      }
    }
  }

  private static Class<? extends MockProvider> autoDetectMockProviderClass() {
    final List<String> providers = Arrays.asList("org.needle4j.mock.MockitoProvider", "org.needle4j.mock.EasyMockProvider");

    for (final String p : providers) {
      try {
        return ReflectionUtil.lookupClass(MockProvider.class, p);
      } catch (final Exception e) {
        // ignored. provider not available due to missing dependencies.
      }
    }

    throw new RuntimeException("no mock provider configured");
  }

  @SuppressWarnings("unchecked")
  <T extends MockProvider> T createMockProvider(final Class<? extends MockProvider> mockProviderClass) {
    try {
      return (T) mockProviderClass.getDeclaredConstructor().newInstance();
    } catch (final Exception e) {
      throw new RuntimeException("could not create a new instance of mock provider " + mockProviderClass, e);
    }
  }
}
