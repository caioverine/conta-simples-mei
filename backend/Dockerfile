# Estágio 1: Build da aplicação
FROM gradle:8.4-jdk17-alpine AS build
WORKDIR /app

# Copiar arquivos de configuração do Gradle primeiro (para cache de layers)
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle

# Baixar dependências (layer cached)
RUN gradle build --no-daemon || return 0

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN gradle clean bootJar --no-daemon

# Estágio 2: Runtime da aplicação
FROM openjdk:17-alpine AS production

# Instalar timezone data para configurar timezone
RUN apk add --no-cache tzdata

# Configurar timezone para Brasil
ENV TZ=America/Sao_Paulo

# Criar usuário não-root por segurança
RUN addgroup -S spring && adduser -S spring -G spring

# Criar diretório da aplicação
WORKDIR /app

# Copiar JAR do estágio de build
COPY --from=build /app/build/libs/*.jar app.jar

# Dar permissão para o usuário spring
RUN chown spring:spring app.jar

# Trocar para usuário não-root
USER spring

# Configurar JVM para containers
ENV JAVA_OPTS="-Xms128m -Xmx512m -XX:+UseContainerSupport -XX:MaxRAMPercentage=70.0"

# Porta que a aplicação vai usar
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
