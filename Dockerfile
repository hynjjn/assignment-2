# --- Build stage: compile + package the WAR with Maven ---
FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
COPY sql ./sql
# Point JDBC at the compose DB service ("db") instead of localhost (build copy only).
RUN sed -i 's#localhost:3306#db:3306#' src/main/resources/db.properties
RUN mvn -q clean package

# --- Runtime stage: Tomcat 9 (javax.servlet namespace) ---
FROM tomcat:9.0-jdk11-temurin
# Deploy as /opinion-poll context to match the README URLs.
COPY --from=build /app/target/opinion-poll.war /usr/local/tomcat/webapps/opinion-poll.war
EXPOSE 8080
