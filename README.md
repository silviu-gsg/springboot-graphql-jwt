# GraphQL Books API secured with Spring Security via JWTs

## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Building and running the application](#building-and-running-the-application)
4. [Notes](#notes)

### General Info
Demo Spring Boot project with a GraphQL API secured with JWT.

### Technologies
The following main technologies were used:
* [Spring Boot](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/): Version 2.4.3
* [Spring Security](https://docs.spring.io/spring-security/site/docs/5.4.5/reference/html5/): Version 5.4.5
* [GraphQL](https://graphql.org/code/#java-kotlin): Version 0.0.4
* [JJWT](https://java.jsonwebtoken.io/jjwtdocs.html): Version 0.10.7

### Building and running the application

The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build and run the project just execute the following command:

```bash
  ./gradlew bootRun
```

It will start the Spring Boot module on port 8080 and it will use the embedded in-memory H2 database.

### Notes

After the server is up and running, you can test the JWT auth flows by obtaining a JWT token via a POST call at:
```
http://localhost:8080/login
```
with username & password in the request body - see demo predefined users in:
``
UserDetailsRepository.java
``
Then you can test the GraphQL queries and mutations via POST calls at:
````
http://localhost:8080/graphql
````