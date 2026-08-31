# Start with a base image containing the Java runtime
FROM eclipse-temurin:25-jdk-noble

# Info around who maintains the image
LABEL maintainer="Ojus M S"

# Add the application's Jar to the image. This was earlier created using '.\mvnw clean package' in the terminal
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]