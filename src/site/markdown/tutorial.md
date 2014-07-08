# Step-by-step tutorial

Writing good concise tests for application components can be quite cumbersome and difficult. Especially, 
when they have a lot of dependencies to other beans and you do not want to manually write mock objects to fulfil 
all of them. On the other hand, you do not want to run too many slow integration tests. 

With Needle you can accomplish those goals in very comfortable way. 

This tutorial demonstrates how to test Java EE based applications with Needle and shows you how easy it is to 
write unit tests with Needle. For this purpose we will implement a simple Java EE 6 Blog application with JPA, 
EJB, CDI and JSF step-by-step and test the components.

## Project Setup

Let’s start by generating a maven project skeleton with a maven archetype.

```
mvn archetype:generate \
  -DarchetypeArtifactId=jboss-javaee6-webapp-ear-archetype-blank \
  -DarchetypeGroupId=org.jboss.spec.archetypes \
  -DarchetypeVersion=7.0.2.CR2
```
     
The generated application is a multi-module maven project, packaged as an ear archive. It includes nothing else 
as the complete maven configuration for a modular Java EE 6 application. To build and package the application use 
the maven command mvn clean package.

