# Build stage - executado no GitHub Actions
FROM gradle:8.7.0-jdk17 AS build

# Instalar apenas o necessário
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar arquivos de configuração
COPY settings.gradle.kts build.gradle.kts ./
COPY gradlew ./
COPY gradle/ ./gradle/
RUN chmod +x gradlew

# Download de dependências com recursos do GitHub Actions
RUN ./gradlew --no-daemon --max-workers=1 dependencies || true

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN ./gradlew --no-daemon --max-workers=1 build -x test --stacktrace \
    && rm -rf ~/.gradle/caches/modules-2/modules-2.lock \
    && rm -rf ~/.gradle/caches/*/plugin-resolution/ \
    && rm -rf ~/.gradle/caches/*/scripts/ \
    && rm -rf ~/.gradle/caches/*/scripts-remapped/ \
    && rm -rf ~/.gradle/caches/*/fileHashes/ \
    && find ~/.gradle/caches/ -name "*.lock" -type f -delete

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
RUN groupadd -r appuser && \
    useradd -r -g appuser -d /app -s /bin/bash appuser

# Copiar JAR
COPY --from=build --chown=appuser:appuser /app/build/libs/*.jar app.jar

# Verificar se o JAR existe
RUN ls -la /app/ && test -f app.jar

USER appuser

# Configurar JVM para container
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport"

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