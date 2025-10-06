# base image
FROM eclipse-temurin:21-jdk-jammy

#working directory
WORKDIR /app

# Copy JAR file into the container
COPY target/contacatapi-0.0.1-SNAPSHOT.jar app.jar

#port
EXPOSE 8080

# Command to run
ENTRYPOINT ["java", "-jar", "app.jar"]

