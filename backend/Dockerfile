# Build stage - executado no GitHub Actions
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app

# Copiar arquivos de configuração
COPY settings.gradle.kts build.gradle.kts ./
COPY gradlew ./
COPY gradle/ ./gradle/
RUN chmod +x gradlew

# Download de dependências com recursos do GitHub Actions
RUN ./gradlew --no-daemon dependencies || true

# Copiar código fonte
COPY src ./src

# Build com recursos adequados (GitHub Actions tem 7GB RAM)
RUN ./gradlew build --no-daemon && \
    rm -rf ~/.gradle/caches/

# Runtime stage - otimizado para t2.micro
FROM eclipse-temurin:17-jre

# Instalar apenas o essencial
RUN apt-get update && \
    apt-get install -y curl wget tzdata && \
    rm -rf /var/lib/apt/lists/*

# Configurar timezone
ENV TZ=America/Sao_Paulo
RUN cp /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

# Criar usuário não-root
RUN addgroup -S appuser && \
    adduser -S -D -G appuser appuser

# Copiar JAR
COPY --from=build --chown=appuser:appuser /app/build/libs/*.jar app.jar

# Verificar se o JAR existe
RUN ls -la /app/ && test -f app.jar

USER appuser
EXPOSE 8080

# JVM otimizada para t2.micro (máximo 400MB)
ENTRYPOINT ["java", \
    "-Xmx400m", \
    "-Xms128m", \
    "-XX:MaxMetaspaceSize=64m", \
    "-XX:+UseSerialGC", \
    "-XX:+UseContainerSupport", \
    "-XX:+ExitOnOutOfMemoryError", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=staging", \
    "-jar", "/app/app.jar"]