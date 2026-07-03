# Multi-stage build for Java Spring Boot services
# Usage: docker build --build-arg SERVICE=fx-trade -t fx-trade .

ARG SERVICE=fx-trade

# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY fx-common/pom.xml fx-common/
COPY ${SERVICE}/pom.xml ${SERVICE}/
RUN mvn dependency:go-offline -pl ${SERVICE} -am

COPY . .
RUN mvn package -pl ${SERVICE} -am -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
RUN apk --no-cache add tzdata && cp /usr/share/zoneinfo/UTC /etc/localtime
WORKDIR /app
COPY --from=builder /app/${SERVICE}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
