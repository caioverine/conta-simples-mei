# Etapa de build com Gradle
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app

# Copia apenas arquivos necessários para o build (melhora cache)
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle gradle
COPY src src

RUN ./gradlew build -x test

# Etapa de execução
FROM openjdk:17-jdk-slim
WORKDIR /app
VOLUME /tmp
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
