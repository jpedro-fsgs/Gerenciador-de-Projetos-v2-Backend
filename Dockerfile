FROM maven:3.9.9-eclipse-temurin-22 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim
WORKDIR /app
VOLUME /tmp
COPY --from=build /app/target/*.jar /app/gerenciador-de-projetos-v2-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/gerenciador-de-projetos-v2-backend-0.0.1-SNAPSHOT.jar"]