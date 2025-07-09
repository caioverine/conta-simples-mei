# ===== MULTI-STAGE BUILD OTIMIZADO =====
# Stage 1: Cache de dependências
FROM gradle:8-jdk17-alpine AS dependencies
WORKDIR /app

# Copiar apenas arquivos de configuração do Gradle
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle/ ./gradle/

# Configurar Gradle para performance
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx1g -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -XX:+UseStringDeduplication -Dorg.gradle.caching=true -Dorg.gradle.parallel=true -Dorg.gradle.configureondemand=true"

# Baixar dependências (será cached pelo Docker)
RUN --mount=type=cache,target=/root/.gradle/caches \
    --mount=type=cache,target=/root/.gradle/wrapper \
    gradle --no-daemon --max-workers=1 dependencies --refresh-dependencies || true

# Stage 2: Build da aplicação
FROM gradle:8-jdk17-alpine AS builder
WORKDIR /app

# Copiar cache de dependências do stage anterior
COPY --from=dependencies /root/.gradle /root/.gradle

# Copiar arquivos de configuração
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle/ ./gradle/

# Copiar código fonte
COPY src/ ./src/

# Configurar Gradle para performance
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx1g -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -XX:+UseStringDeduplication -Dorg.gradle.caching=true -Dorg.gradle.parallel=true -Dorg.gradle.configureondemand=true"

# Build da aplicação usando cache
RUN --mount=type=cache,target=/root/.gradle/caches \
    --mount=type=cache,target=/root/.gradle/wrapper \
    gradle --no-daemon --max-workers=1 build -x test --build-cache

# Stage 3: Runtime otimizado
FROM openjdk:17-jre-alpine AS runtime
WORKDIR /app

# Instalar dependências mínimas
RUN apk --no-cache add curl

# Criar usuário não-root
RUN addgroup -g 1000 appuser && adduser -u 1000 -G appuser -s /bin/sh -D appuser

# Copiar JAR da aplicação
COPY --from=builder /app/build/libs/*.jar app.jar

# Configurar ownership
RUN chown -R appuser:appuser /app

# Usar usuário não-root
USER appuser

# Configurar JVM para container
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+OptimizeStringConcat -XX:+UseCompressedOops -XX:MaxGCPauseMillis=100 -Djava.security.egd=file:/dev/./urandom"

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Expor porta
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]