# Dockerfile otimizado especificamente para t2.micro (1GB RAM)
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app

# ===== CONFIGURAÇÕES CRÍTICAS PARA LOW-MEMORY =====
# Limitar uso de memória do Gradle drasticamente
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.jvmargs=-Xmx512m -Dorg.gradle.workers.max=1 -Dorg.gradle.parallel=false"
ENV JAVA_OPTS="-Xmx256m -XX:MaxMetaspaceSize=128m"

# Copiar arquivos de configuração primeiro
COPY settings.gradle.kts build.gradle.kts ./
COPY gradlew ./
COPY gradle/ ./gradle/
RUN chmod +x gradlew

# Download de dependências com limite de memória
RUN ./gradlew --no-daemon --max-workers=1 dependencies || true

# Copiar código fonte
COPY src ./src

# Build com configurações ultra-conservadoras de memória
RUN ./gradlew clean build -x test \
    --no-daemon \
    --no-parallel \
    --max-workers=1 \
    -Dorg.gradle.jvmargs="-Xmx512m -XX:MaxMetaspaceSize=128m" \
    --info

# ===== RUNTIME ULTRA-LEVE =====
FROM eclipse-temurin:17-jre-alpine AS runtime

# Alpine é muito mais leve que Ubuntu
RUN apk add --no-cache curl tzdata

WORKDIR /app

# Usuário não-root
RUN addgroup -g 1001 -S appuser && \
    adduser -S -D -u 1001 -G appuser appuser

# Copiar JAR
COPY --from=build --chown=appuser:appuser /app/build/libs/*.jar app.jar

USER appuser
EXPOSE 8080

# JVM configurada para usar no máximo 400MB RAM
ENTRYPOINT ["java", \
    "-Xmx400m", \
    "-Xms128m", \
    "-XX:MaxMetaspaceSize=64m", \
    "-XX:+UseSerialGC", \
    "-XX:+UseContainerSupport", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "/app/app.jar"]