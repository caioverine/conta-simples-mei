# Etapa de build com Gradle (com cache otimizado)
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app

# Copia apenas arquivos que mudam com menos frequência primeiro
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradlew .
COPY gradle/ ./gradle/

# Dá permissão ao gradlew e baixa dependências sem copiar o código ainda
RUN chmod +x gradlew
RUN ./gradlew dependencies || true

# Agora sim copia o código-fonte
COPY src ./src

# Build (sem testes, para staging)
RUN ./gradlew build -x test

# Etapa de execução
FROM openjdk:17-jdk-slim
WORKDIR /app
VOLUME /tmp

# Copia somente o JAR gerado
COPY --from=build /app/build/libs/*.jar app.jar

# Expor a porta usada pela aplicação
EXPOSE 8080

# Comando de entrada
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
