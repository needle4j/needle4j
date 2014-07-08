# Configuration

This chapter describes how to set up and configure your **needle4j** tests.

## Requirements

* Ensure that you have a [JDK6+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed.

## Maven dependency configuration

If you are using [Maven](http://maven.apache.org/) as your build tool add the following single dependency to your 
pom.xml file to get started with Needle4j (see [dependency info](dependency-info.html)):

```xml
<dependency>
   <groupId>org.needle4j</groupId>
   <artifactId>needle4j</artifactId>
   <scope>test</scope>
   <version>${needle4j.version}</version>
</dependency>
```

Where `needle4j.version` currently should have the value “${project.version}”. Check for
the most current version at the [maven central repo](http://mvnrepository.com/artifact/org.needle4j/needle4j).


To reduce complexity Needle4j has no transitive dependencies, and does
thus not restrict you to use specific versions of JUnit or TestNG. On
the other hand, you will have to explicitly configure dependencies for
the test scope, for example, to Hibernate as the JPA provider.

## Needle configuration properties

The Needle4j default configuration may be modified in a `needle.properties` file in the classpath root. 
I.e., Needle will look for a file `/needle.properties` somewhere in the classpath.

### Configuration of additional custom injection annotations and injection provider.

Property Name  | Description
------------- | -------------
custom.injection.annotations  | Comma separated list of the fully qualified name of the annotation classes. A standard mock provider will be created for each annotation.
custom.injection.provider.classes | Comma separated list of the fully qualified name of the injection provider implementations.
custom.instances.supplier.classes | Comma separated list of the fully qualified name of the instances supplier implementations.

### Configuration of mock provider.

Property Name  | Description
------------- | -------------
mock.provider | The fully qualified name of an implementation of the MockProvider interface. There is an implementation of EasyMock
org.needle4j.mock.EasyMockProvider and Mockito org.needle4j.mock.MockitoProvider. **EasyMock is the default configuration.**

### Configuration of JPA, Database operation and JDBC connection.

Property Name  | Description
------------- | -------------
mock.provider | The fully qualified name of an implementation of the MockProvider interface. There is an implementation of EasyMock org.needle4j.mock.EasyMockProvider and Mockito org.needle4j.mock.MockitoProvider. **EasyMock is the default configuration.**
persistenceUnit.name | The persistence unit name. Default is TestDataModel
hibernate.cfg.filename | XML configuration file to configure Hibernate (eg. /hibernate.cfg.xml)
db.operation | Optional database operation on test setup and tear down. Value is the fully qualified name of an implementation of the
AbstractDBOperation base class. There is an implementation for script execution org.needle4j.db.operation.ExecuteScriptOperation
and for the HSQL DB to delete all tables org.needle4j.db.operation.hsql.HSQLDeleteOperation.
jdbc.url | The JDBC driver specific connection url.
jdbc.driver | The fully qualified class name of the driver class.
jdbc.user | The JDBC user name used for the database connection.
jdbc.password | The JDBC password used for the database connection.

The JDBC configuration properties are only required if database operation and JPA 1.0 are used. Otherwise, the JDBC properties are
related to the standard property names of JPA 2.0.

### Example configuration

A typical `needle.properties` file might look like this:

```
db.operation=de.akquinet.jbosscc.needle.db.operation.hsql.HSQLDeleteOperation
jdbc.driver=org.hsqldb.jdbcDriver
jdbc.url=jdbc:hsqldb:mem:memoryDB
jdbc.user=sa
jdbc.password=
```

## Logging

Needle4j uses the Simple Logging Facade for Java (SLF4J).
[SLF4J](http://www.slf4j.org/manual.html) serves as a simple facade or
abstraction for various logging frameworks. The SLF4J distribution ships
with several JAR files referred to as “SLF4J bindings”, with each
binding corresponding to a supported framework.

For logging within the test, the following optional dependency may be
added to the classpath:

```xml
<dependency>
   <groupId>org.slf4j</groupId>
   <artifactId>slf4j-simple</artifactId>
   <scope>test</scope>
</dependency>
```
