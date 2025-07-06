# Etapa de build com Gradle (com cache otimizado)
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app

# Copia arquivos de configuração do Gradle
COPY settings.gradle.kts build.gradle.kts ./
COPY gradlew ./
COPY gradle/ ./gradle/

# Dá permissão ao gradlew
RUN chmod +x gradlew

# Cria um build.gradle.kts temporário para baixar dependências
# Isso é mais eficiente que usar './gradlew dependencies'
RUN ./gradlew build --dry-run --no-daemon || true

# Agora copia o código-fonte
COPY src ./src

# Build da aplicação (sem testes para staging)
RUN ./gradlew build -x test --no-daemon

# Etapa de execução (runtime)
FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app

# Cria usuário não-root para segurança
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copia apenas o JAR gerado
COPY --from=build /app/build/libs/*.jar app.jar

# Muda ownership para o usuário não-root
RUN chown appuser:appuser app.jar

# Muda para usuário não-root
USER appuser

# Expor a porta da aplicação
EXPOSE 8080

# Configurações JVM otimizadas para container
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+ExitOnOutOfMemoryError", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "/app/app.jar"]