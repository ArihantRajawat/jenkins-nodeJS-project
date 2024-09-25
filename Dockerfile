# Stage 1: Build the Java application
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Set up the MySQL database
FROM mysql:8.0 AS mysql

# Set environment variables for MySQL
ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_DATABASE=mydb
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=userpassword

# Stage 3: Run the Java application
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/DK-Hospital-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables for the application to connect to MySQL
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/mydb
ENV SPRING_DATASOURCE_USERNAME=user
ENV SPRING_DATASOURCE_PASSWORD=userpassword

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]