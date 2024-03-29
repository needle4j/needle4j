package org.needle4j;

import org.needle4j.annotation.InjectInto;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.common.MapEntry;
import org.needle4j.injection.InjectionConfiguration;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionTargetInformation;
import org.needle4j.mock.MockProvider;
import org.needle4j.mock.SpyProvider;
import org.needle4j.predicate.IsSupportedAnnotationPredicate;
import org.needle4j.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

/**
 * Abstract test case to process and initialize all fields annotated with
 * {@link ObjectUnderTest}. After initialization, {@link InjectIntoMany} and
 * {@link InjectInto} annotations are processed for optional additional
 * injections.
 * <p>
 * Supported injections are:
 * </p>
 * <ol>
 * <li>Constructor injection</li>
 * <li>Field injection</li>
 * <li>Method injection</li>
 * </ol>
 *
 * @see ObjectUnderTest
 * @see InjectInto
 * @see InjectIntoMany
 * @see InjectionProvider
 *
 * @author Heinz Wilming, Alphonse Bendt, Markus Dahm Akquinet AG
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
public abstract class NeedleTestcase {
  private static final Logger LOG = LoggerFactory.getLogger(NeedleTestcase.class);

  private final InjectionConfiguration configuration;
  private final IsSupportedAnnotationPredicate isSupportedAnnotationPredicate;

  private NeedleContext context;

  /**
   * Create an instance of {@link NeedleTestcase} with optional additional
   * injection provider.
   *
   * @param injectionProviders optional additional injection provider
   * @see InjectionProvider
   */
  protected NeedleTestcase(final InjectionProvider<?>... injectionProviders) {
    this(new InjectionConfiguration(), injectionProviders);
  }

  protected NeedleTestcase(final InjectionConfiguration configuration,
                           final InjectionProvider<?>... injectionProviders) {
    this.configuration = configuration;
    this.isSupportedAnnotationPredicate = new IsSupportedAnnotationPredicate(configuration);

    addInjectionProvider(injectionProviders);
  }

  protected final void addInjectionProvider(final InjectionProvider<?>... injectionProvider) {
    configuration.addInjectionProvider(injectionProvider);
  }

  /**
   * Initialize all fields annotated with {@link ObjectUnderTest}. Is an
   * object under test annotated field already initialized, only the injection
   * of dependencies will be executed. After initialization,
   * {@link InjectIntoMany} and {@link InjectInto} annotations are processed
   * for optional additional injections.
   *
   * @param test an instance of the test
   * @throws Exception thrown if an initialization error occurs.
   */
  protected final void initTestcase(final Object test) throws Exception {
    LOG.info("init testcase {}", test);

    context = new NeedleContext(test);

    final List<Field> fields = context.getAnnotatedTestcaseFields(ObjectUnderTest.class);

    LOG.debug("found fields {}", fields);
    for (final Field field : fields) {
      LOG.debug("found field {}", field.getName());
      final ObjectUnderTest objectUnderTestAnnotation = field.getAnnotation(ObjectUnderTest.class);

      try {
        final Object instance = setInstanceIfNotNull(field, objectUnderTestAnnotation, test);
        initInstance(instance);
      } catch (final InstantiationException | IllegalAccessException e) {
        LOG.error(e.getMessage(), e);
      }
    }

    configuration.getChainedNeedleProcessor().process(context);
    // TODO find way to include postconstruct processor in chain
    beforePostConstruct();
    configuration.getPostConstructProcessor().process(context);
  }

  public NeedleContext getContext() {
    return context;
  }

  /**
   * init mocks
   */
  protected void beforePostConstruct() {
  }

  /**
   * Inject dependencies into the given instance. First, all field injections
   * are executed, if there exists an {@link InjectionProvider} for the
   * target. Then the method injection is executed, if the injection
   * annotations are supported.
   *
   * @param instance the instance to initialize.
   */
  protected final void initInstance(final Object instance) {
    initFields(instance);
    initMethodInjection(instance);
  }

  private void initMethodInjection(final Object instance) {
    final List<Method> methods = ReflectionUtil.getMethods(instance.getClass());

    for (final Method method : methods) {
      // if the method is not annotated with at least one supported
      // annotation, skip it!
      if (!isSupportedAnnotationPredicate.applyAny(method.getDeclaredAnnotations())) {
        continue;
      }

      final Class<?>[] parameterTypes = method.getParameterTypes();

      final InjectionTargetInformationFactory injectionTargetInformationFactory =
          (parameterType, parameterIndex) -> new InjectionTargetInformation(parameterType, method,
              method.getGenericParameterTypes()[parameterIndex],
              method.getParameterAnnotations()[parameterIndex]);
      final Object[] arguments = createArguments(parameterTypes, injectionTargetInformationFactory);

      try {
        ReflectionUtil.invokeMethod(method, instance, arguments);
      } catch (final Exception e) {
        LOG.warn("could not invoke method", e);
      }
    }
  }

  private Object[] createArguments(final Class<?>[] parameterTypes,
                                   final InjectionTargetInformationFactory injectionTargetInformationFactory) {
    final Object[] arguments = new Object[parameterTypes.length];

    for (int i = 0; i < parameterTypes.length; i++) {
      final InjectionTargetInformation injectionTargetInformation = injectionTargetInformationFactory.create(
          parameterTypes[i], i);

      final Entry<Object, Object> injection = inject(injectionTargetInformation);

      if (injection != null) {
        arguments[i] = injection.getValue();
      }

    }

    return arguments;
  }

  private void initFields(final Object instance) {
    final List<Field> fields = ReflectionUtil.getAllFieldsWithSupportedAnnotation(instance.getClass(),
        isSupportedAnnotationPredicate);

    for (final Field field : fields) {
      final InjectionTargetInformation injectionTargetInformation = new InjectionTargetInformation(
          field.getType(), field);

      final Entry<Object, Object> injection = inject(injectionTargetInformation);

      if (injection != null) {
        try {
          ReflectionUtil.setField(field, instance, injection.getValue());

        } catch (final Exception e) {
          LOG.error(e.getMessage(), e);
        }
      }
    }
  }

  private Object setInstanceIfNotNull(final Field field, final ObjectUnderTest objectUnderTestAnnotation,
                                      final Object test) throws Exception {
    final SpyProvider spyProvider = configuration.getSpyProvider();
    final String id = objectUnderTestAnnotation.id().equals("") ? field.getName() : objectUnderTestAnnotation.id();

    Object instance = ReflectionUtil.getFieldValue(test, field);

    if (instance == null) {

      final Class<?> implementation = objectUnderTestAnnotation.implementation() != Void.class ? objectUnderTestAnnotation
          .implementation() : field.getType();

      if (implementation.isInterface()) {
        throw new ObjectUnderTestInstantiationException("could not create an instance of object under test "
            + implementation + ", no implementation class configured");
      }

      instance = getInstanceByConstructorInjection(implementation);

      if (instance == null) {
        instance = createInstanceByNoArgConstructor(implementation);
      }

      // create spy if required, else just return unmodified instance
      if (spyProvider.isSpyRequested(field)) {
        instance = spyProvider.createSpyComponent(instance);
      }
      setField(field, test, instance);
    }
    // field value was already set in test
    else {
      if (spyProvider.isSpyRequested(field)) {
        setField(field, test, spyProvider.createSpyComponent(instance));
      }
    }

    context.addObjectUnderTest(id, instance, objectUnderTestAnnotation);

    return instance;
  }

  private void setField(final Field field, final Object test, final Object instance)
      throws ObjectUnderTestInstantiationException {
    try {
      ReflectionUtil.setField(field, test, instance);
    } catch (final Exception e) {
      throw new ObjectUnderTestInstantiationException(e);
    }
  }

  protected Object createInstanceByNoArgConstructor(final Class<?> implementation)
      throws ObjectUnderTestInstantiationException {
    try {
      implementation.getConstructor();

      return implementation.getDeclaredConstructor().newInstance();
    } catch (final NoSuchMethodException e) {
      throw new ObjectUnderTestInstantiationException("could not create an instance of object under test "
          + implementation + ",implementation has no public no-arguments constructor", e);
    } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new ObjectUnderTestInstantiationException(e);
    }
  }

  protected Object getInstanceByConstructorInjection(final Class<?> implementation)
      throws ObjectUnderTestInstantiationException {
    final Constructor<?>[] constructors = implementation.getConstructors();

    for (final Constructor<?> constructor : constructors) {
      // has the constructor at least one supported injection annotation?
      if (!isSupportedAnnotationPredicate.applyAny(constructor.getAnnotations())) {
        continue;
      }

      final Class<?>[] parameterTypes = constructor.getParameterTypes();
      final InjectionTargetInformationFactory injectionTargetInformationFactory =
          (parameterType, parameterIndex) -> new InjectionTargetInformation(parameterType, constructor,
              constructor.getGenericParameterTypes()[parameterIndex],
              constructor.getParameterAnnotations()[parameterIndex]);

      final Object[] arguments = createArguments(parameterTypes, injectionTargetInformationFactory);

      try {
        return constructor.newInstance(arguments);
      } catch (final Exception e) {
        throw new ObjectUnderTestInstantiationException(e);
      }
    }

    return null;
  }

  /**
   * Returns the injected object for the given key, or null if no object was
   * injected with the given key.
   *
   * @param key the key of the injected object, see
   *            {@link InjectionProvider#getKey(InjectionTargetInformation)}
   * @return the injected object or null
   */
  public <X> X getInjectedObject(final Object key) {
    return context.getInjectedObject(key);
  }

  /**
   * Returns an instance of the configured {@link MockProvider}
   *
   * @return the configured {@link MockProvider}
   */
  public <X extends MockProvider> X getMockProvider() {
    return configuration.getMockProvider();
  }

  private interface InjectionTargetInformationFactory {
    InjectionTargetInformation create(Class<?> parameterType, int parameterIndex);
  }

  private Entry<Object, Object> inject(final InjectionTargetInformation injectionTargetInformation) {
    for (final Collection<InjectionProvider<?>> collection : configuration.getInjectionProvider()) {
      final Entry<Object, Object> injection = configuration.handleInjectionProvider(collection,
          injectionTargetInformation);
      if (injection != null) {

        final Object injectionKey = injection.getKey();
        // check if mock object already created
        final Object injectionValue = context.getInjectedObject(injectionKey) == null ? injection.getValue()
            : context.getInjectedObject(injectionKey);

        context.addInjectedObject(injectionKey, injectionValue);
        return new MapEntry<>(injectionKey, injectionValue);
      }
    }

    return null;
  }
}
