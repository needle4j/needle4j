[![Build Status](https://secure.travis-ci.org/needle4j/needle4j.png)](https://travis-ci.org/needle4j/needle4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.needle4j/needle4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.needle4j/needle4j)


# Need(le) for Speed - Effective Unit Testing for Java EE

[@NeedleProject](https://twitter.com/NeedleProject)

**Needle4j is a lightweight framework for testing Java EE components outside of the container in isolation. It reduces the test setup code by
analysing dependencies and automatic injection of mock objects. It will thus maximize the speed of development as well as the execution of unit
tests.**

![Needle Coffee Cups](https://needle.spree.de/images/needle-coffeecups-380px.jpg)

## Core Features:

* Instantiation of @ObjectUnderTest Components
* Constructor, Method and Field based dependency injection
* Injection of Mock objects by default
* Extensible by providing custom injection providers
* Wiring of object graphs

* Database testing via JPA Provider, e.g. EclipseLink or Hibernate
* EntityManager creation and injection
* Execute optional database operations during test setup and tear down
* Transaction Utilities

* Provide Utilities for Reflection, e.g. for private method invocation or field access

* Needle can be used with [JUnit](http://www.junit.org/) or [TestNG](http://testng.org/).
* It supports [EasyMock](http://www.easymock.org/) and [Mockito](http://code.google.com/p/mockito/) out-of-the-box.

## Getting started

Add the following dependencies to your pom.xml file to get started using Needle:

```
<dependency>
    <groupId>org.needle4j</groupId>
    <artifactId>needle4j</artifactId>
    <version>2.3</version>
    <scope>test</scope>
</dependency>

(plus junit, mockito, ...)
``` 

Implementing your first Needle Test:

```
public class UserDaoTest {

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    @Rule
    public NeedleRule needleRule = new NeedleRule(databaseRule);

    @ObjectUnderTest
    private UserDao userDao;

    @Test
    public void testFindByUsername() throws Exception {
        final User user = new UserTestdataBuilder(
        databaseRule.getEntityManager()).buildAndSave();

        User userFromDb =
            userDao.findBy(user.getUsername(), user.getPassword());

        Assert.assertEquals(user.getId(), userFromDb.getId());
    }
}
``` 

For the documentation and more examples please refer to the maven site.

## Licensing

Needle is licensed under GNU Lesser General Public License (LGPL) version 2.1 or later.

## Needle URLs

* Needle Home Page: http://www.needle4j.org
* Source Code:      https://github.com/needle4j/needle4j
* Issue Tracking:   https://github.com/needle4j/needle4j/issues
* [needle4j@ohloh.net](https://www.ohloh.net/p/needle4j)
* [Gitter chat](https://gitter.im/needle4j)

## Release Nodes

Version 3.0 - Upgrade to JDK 11 and Hibernate 5.6, Removal of deprecated code, cleanup code

Version 2.2 - https://github.com/akquinet/needle/issues?milestone=1&state=closed

Previous Versions - https://github.com/akquinet/needle/blob/master/src/docs/dist/changelog.txt

