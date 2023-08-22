# Use a base image with Java 11
FROM eclipse-temurin:17-jre-alpine

# Set the working directory
WORKDIR /payment-gateway

# Copy the Spring Boot application JAR file into the container
COPY build/libs/payment-gateway-0.0.1-SNAPSHOT.jar payment-gateway.jar

# Expose the port that your Spring Boot application runs on
EXPOSE 8080

# Run the Spring Boot application when the container starts
CMD ["java", "-jar", "payment-gateway.jar"]
