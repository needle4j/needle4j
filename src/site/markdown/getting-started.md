# Getting Started

In this chapter, a very simple user management application is to be tested using **needle4j**.

## Sample Application

The example consists of two JPA entity classes User and Profile, with a @OneToOne relationship between them and two CDI components.

### User

```java
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToOne(optional = false, cascade = CascadeType.ALL)
  private Profile profile;

  // Getters and setters
  ...
}
```

### Profile

```java
@Entity
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String language;

  // Getters and setters
  ...
}
```

### Data Access Object

Now we add a simple DAO component to access the user data.

```java
public class UserDao {
  @PersistenceContext
  private EntityManager entityManager;

  public User findBy(final String username, final String password) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);

    Root<User> user = query.from(User.class);

    query.where(
      builder.and(builder.equal(user.get(User_.username), username)),
      builder.equal(user.get(User_.password), password));

      return entityManager.createQuery(query).getSingleResult();
  }

  public List<User> findAll() {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);

    return entityManager.createQuery(query).getResultList();
  }
}
```

### Authenticator

To authenticate a user, the application uses an authenticator component which itself depends on the User DAO.

```java
public class Authenticator {
  @Inject
  private UserDao userDao;

  public boolean authenticate(final String username, final String password) {
    User user = userDao.findBy(username, password);

    return user != null ? true : false;
  }
}
```

## Using Needle with JUnit

Needle4j provides JUnit “Rules” to extend JUnit. Rules are basically wrappers around test methods. They may execute 
code before, after or instead of a test method.

The following example demonstrates hwo to write a simple JUnit Needle
test with two rules. The database rule provides access to the database
via JPA and may execute optional database operations, e.g. to setup the
initial data. The Needle rule does the real magic: it scans the test for
all fields annotated with `@ObjectUnderTest` and initializes these
tested components by injection of their dependencies. I.e., the UserDao
will get the `EntityManager` field injected automatically. Since we
provided a database rule that entity manager will not be a mock, but a
“real” entity manager.

Supported injections are constructor injection, field injection and
method injection.

```java
public class UserDaoTest {
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Rule
  public NeedleRule needleRule = new NeedleRule(databaseRule);

  @ObjectUnderTest
  private UserDao userDao;

  @Test
  public void testFindByUsername() throws Exception {
    final User user = new UserTestdataBuilder(databaseRule.getEntityManager()).buildAndSave();
    User findBy = userDao.findBy(user.getUsername(), user.getPassword());
    Assert.assertEquals(user.getId(), findBy.getId());
  }

  @Test
  public void testFindAll() throws Exception {
    new UserTestdataBuilder(databaseRule.getEntityManager()).buildAndSave();

    List<User> all = userDao.findAll();

    Assert.assertEquals(1, all.size());
  }
}
```

## Using Needle with TestNG

Needle4j also supports TestNG. There are two abstract test cases that may be extended by concrete test classes.

The class `org.needle4j.testng.AbstractNeedleTestcase` scans all fields annotated with `@ObjectUnderTest` and 
initializes the components.

The class `org.needle4j.testng.DatabaseTestcase` can either be used as a special provider for `EntityManager` injection 
or as a base test case for JPA tests. In the first case, a new DatabaseTestcase instance is passed to the constructor of
the AbstractNeedleTestcase:

```java
public class UserDaoTest extends AbstractNeedleTestcase {
  public UserDaoTest() {
    super(new DatabaseTestcase());
  }

  @ObjectUnderTest
  private UserDao userDao;

  @Test
  public void testFindByUsername() throws Exception {
    final User user = new UserTestdataBuilder(getEntityManager()).buildAndSave();

    User findBy = userDao.findBy(user.getUsername(), user.getPassword());
    Assert.assertEquals(user.getId(), findBy.getId());
  }

  @Test
  public void testFindAll() throws Exception {
    new UserTestdataBuilder(getEntityManager()).buildAndSave();

    List<User> all = userDao.findAll();
    Assert.assertEquals(1, all.size());
  }
}
```
