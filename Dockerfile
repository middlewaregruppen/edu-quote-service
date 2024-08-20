# Step 1: Build the application
# Use a Gradle image with OpenJDK 11 to build the Spring Boot application
FROM gradle:7.5.1-jdk11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the Gradle wrapper and settings files first to leverage Docker cache
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./build.gradle
COPY gradle.properties ./gradle.properties
COPY settings.gradle ./settings.gradle

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy the source code and build the application
COPY src ./src
RUN ./gradlew build -x test --no-daemon

# Step 2: Run the application
# Use a lightweight OpenJDK 11 image to run the Spring Boot application
FROM openjdk:11-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]