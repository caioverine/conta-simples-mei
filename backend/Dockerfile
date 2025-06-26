# Etapa de build com Gradle
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app

# Copia apenas arquivos necessários para o build (melhora cache)
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle gradle
COPY src src

RUN gradle bootJar --no-daemon

# Etapa de execução
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
