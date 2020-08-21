# code-with-quarkus project

This project demostrates the Quarkus microservices framework to host java microservices that can be up & running in milliseconds.

It can be run as a standalone service or on graalvm native image to make it cloud native. 

## Prerequisites

1. Install Java and Maven
2. Set JAVA_HOME environment variable to the location where JDK is installed
3. Set MAVEN_HOME environment variable to the location where maven is installed
4. Add Java_HOME/bin to PATH environment variable
5. Add MAVEN_HOME/bin to PATH environment variable
6. Install maven wrapper plugin at the project folder level by issuing the following command : mvn -N io.takari:maven:wrapper

## Compiling the application  and running in dev mode
1. Issue the command from folder where pom.xml is present - mvn compile quarkus:dev

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Launching the application
Launch the following URL in the browser once the server is up - http://localhost:8080/index.html

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `code-with-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.