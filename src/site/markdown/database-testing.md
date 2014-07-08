# Database Testing

When unit-testing your application, it is usually recommended to mock calls to DAOs oder database dependent services in 
your test cases. However, you will also need to test against a real database, e.g. to make sure that your queries work 
as expected.

## Database Testcase

In these cases, Needle can automatically create and inject the
`EntityManager` instance into your objects under test. You simply need
to provide a JPA persistence.xml and the JDBC driver to the classpath of
the test execution. The `persistence.xml` file is the standard
configuration file in JPA. The `persistence.xml` file must define a
persistence-unit with a unique name and the transaction type
RESOURCE_LOCAL for a Java SE environment.

The following listing below shows a complete example of a `persistence.xml` file.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<persistence xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
   version="2.0" xmlns="http://java.sun.com/xml/ns/persistence">
   <persistence-unit name="TestDataModel" transaction-type="RESOURCE_LOCAL">

   <class>fqn.User</class>
   <class>fqn.Profile</class>

   <properties>
     <property name="javax.persistence.jdbc.driver" value="org.hsqldb.jdbcDriver" />
     <property name="javax.persistence.jdbc.url" value="jdbc:hsqldb:." />
     <property name="javax.persistence.jdbc.user" value="sa" />
     <property name="javax.persistence.jdbc.password" value="" />
     <!-- EclipseLink should create the database schema automatically -->
     <property name="eclipselink.ddl-generation" value="create-tables" />
     <property name="eclipselink.ddl-generation.output-mode" value="database" />
   </properties>

   </persistence-unit>
</persistence>
```

The UserTest below checks the JPA mapping against a real database.

```java
public class UserTest {
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void testPersist() throws Exception {
    EntityManager entityManager = databaseRule.getEntityManager();
    User user = new UserTestdataBuilder(entityManager).buildAndSave();
    User userFromDb = entityManager.find(User.class, user.getId());
    Assert.assertEquals(user.getId(), userFromDb.getId());
  }
}
```

Note: The persistence context is cleared after each test!

## Transaction utilities

The EntityManager is the primary interface used by application
developers to interact with the underlying database. Many operations
must be executed in a transaction which is not started in the test
component, because it is usually maintained by the application server.
To run your code using transactions the `TransactionHelper` Utility
makes this more convenient.

```java
public class UserTest {
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();
       
  private TransactionHelper transactionHelper = databaseRule.getTransactionHelper();

  @Test
  public void testPersist() throws Exception {
    final User user = new User();
    ... 
    transactionHelper.executeInTransaction(new VoidRunnable() {
      @Override
      public void doRun(EntityManager entityManager) throws Exception {
        entityManager.persist(user);
      }
    });   
  }
 }
```

The above example illustrates the use of the `TransactionHelper` class and the execution of a transaction. The 
implementation of the VoidRunnable is executed within a transaction. There is also `Runnable<T>` interface that contains 
a method run return a value to be used for further testing.

## Database operation

A common issue in unit tests that access a real database is their effect
on the state of the persistence store. They usually expect a certain
state at the beginning and alter it at runtime which of course will
affect other tests. To address that problem optional Database operations
can be executed before and after test execution, in order to clean up or
set up data.

There are the following implementations:

Class  | Description
------------- | -------------
org.needle4j.db.operation.ExecuteScriptOperation  | Execute sql scripts during test setup and tear down. A before.sql and after.sql script must be provided on the classpath.
org.needle4j.db.operation.h2.H2DeleteOperation | Deletes all rows of all tables of the h2 database.
org.needle4j.db.operation.hsql.HSQLDeleteOperation` | Deletes all rows of all tables of the hsql database.

To use own Database operation implementations, the abstract base class `org.needle4j.db.operation.AbstractDBOperation` 
must be implemented and configured in the `needle.properties` file.

## Testdatabuilder

Using the Test Data Builder pattern, the builder class provides methods
that can be used to configure the test objects. Properties that are not
configured use default values. The builder methods can be chained
together and provide transient or persistent testdata for the test case.

For this purpose Needle provides an abstract base class. The following
code examples shows two implementations of Test Data Builder pattern.
The Testdatabuilder inherit from 'org.needle4j.db.testdata.AbstractTestdataBuilder' class.

```java
public class UserTestdataBuilder extends AbstractTestdataBuilder<User> {

  private String withUsername;
  private String withPassword;
  private Profile withProfile;

  public UserTestdataBuilder() {
    super();
  }

  public UserTestdataBuilder(EntityManager entityManager) {
    super(entityManager);
  }

  public UserTestdataBuilder withUsername(final String username) {
    this.withUsername = username;
    return this;
  }

  private String getUsername() {
    return withUsername != null ? withUsername : "username";
  }

  public UserTestdataBuilder withPassword(final String password) {
    this.withPassword = password;
    return this;
  }

  private String getPassword() {
    return withPassword != null ? withPassword : "password";
  }

  public UserTestdataBuilder withProfile(final Profile profile) {
    this.withProfile = profile;
    return this;
  }

  private Profile getProfile() {
    if (withProfile != null) {
      return withProfile;
    }
    return hasEntityManager() 
           ? new ProfileTestdataBuilder(getEntityManager()).buildAndSave()
           : new ProfileTestdataBuilder().build();
  }

  @Override
  public User build() {
     User user = new User();
     user.setUsername(getUsername());
     user.setPassword(getPassword());
     user.setProfile(getProfile());
     return user;
  }
}
```

```java
public class ProfileTestdataBuilder extends AbstractTestdataBuilder<Profile> {

  private String withLanguage;
       
  public ProfileTestdataBuilder() {
    super();
  }

  public ProfileTestdataBuilder(EntityManager entityManager) {
    super(entityManager);
  }

  public ProfileTestdataBuilder withLanguage(final String language) {
    this.withLanguage = language;
    return this;
  }

  private String getLanguage() {
    return withLanguage != null ? withLanguage : "de";
  }

  @Override
  public Profile build() {
    Profile profile = new Profile();
    profile.setLanguage(getLanguage());
    return profile;
  }
}
```

In the example the Testdatabuilder is using defaults for everything except the username.

    final User user = new UserTestdataBuilder(databaseRule.getEntityManager()).withUsername("user").buildAndSave();

    final User user = new UserTestdataBuilder().withUsername("user").build()

## Injectable database resources

In combination with the NeedleRule, the following resources are injectable by the DatabaseTestcase.

1.  EntityManager
1.  EntityManagerFactory
1.  EntityTransaction
1.  TransactionHelper

Example:

```java
public class UserDaoTest{
        
  @Rule
  public NeedleRule needleRule = new NeedleRule().withOuter(new DatabaseRule());        
  //  Alternative:
  // 
  //  @Rule
  //  public DatabaseRule dbRule = new DatabaseRule()
  //  @Rule
  //  public NeedleRule needleRule = new NeedleRule(dbRule);
       
  @Inject
  private EntityManager entityManager;

  @Inject
  private EntityTransaction entityTransaction;
  //…
}
```
