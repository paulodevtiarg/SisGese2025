# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
# Usa caminhos relativos ao contexto raiz (sisrep2024/)
COPY ../SysGese2026/pom.xml .
COPY ../SysGese2026/src ./src
RUN mvn clean package -DskipTests

# Runtime stage (igual à Opção 1)
FROM eclipse-temurin:21-jre-jammy
COPY --from=build /build/target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
