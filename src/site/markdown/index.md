# Overview

![needle4j](images/coffee.jpg)

**Needle4j** is a lightweight framework for testing Java EE components outside of the container in isolation. The main goals are the reduction of setup
code and faster executions of tests, especially compared to running embedded or external server tests.

Needle4j will automatically analyse the dependencies of components and inject mock objects by default. The developer may freely provide default or custom objects instead.

Needle4j is an Open Source library, [hosted at github](https://github.com/needle4j/needle4j).

GNU Lesser General Public License (LGPL) version 2.1 or later.

# Features

- Instantiation of tested components
- Constructor, Method and Field based dependency injection
- Injection of Mock objects by default
- Extensible by providing custom injection providers
- Comfortable automatic wiring of dependency graphs
- Database testing via JPA Provider, e.g.[EclipseLink](http://www.eclipse.org/eclipselink/) or [Hibernate](http://www.hibernate.org)
- EntityManager creation and injection
- Execute optional database operations during test setup and tear down
- Transaction Utilities
- Provide Utilities for Reflection, e.g. for private method invocation
    or field access
- Needle can be used with [JUnit](http://www.junit.org) or
    [TestNG](http://testng.org/).
- It supports [EasyMock](http://www.easymock.org/) and
  [Mockito](http://code.google.com/p/mockito/) out-of-the-box but
  could also be extended with other frameworks.

## Links

-   Needle4j Home Page: http://www.needle4j.org/
-   Downloads: http://www.needle4j.org/downloads
-   Issue Tracking: https://github.com/needle4j/needle4j/issues
-   Source Code: https://github.com/needle4j/needle4j
