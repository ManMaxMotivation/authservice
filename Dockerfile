# Ступень 1: Сборка приложения
FROM maven:3.9.6-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Ступень 2: Запуск приложения
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/my spring-boot-app-1.0.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]