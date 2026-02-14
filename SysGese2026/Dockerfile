FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copiar TUDO de uma vez (simples e garantido)
COPY SysGese2026/ .

# OU (mais explícito):
COPY SysGese2026/pom.xml .
COPY SysGese2026/src ./src
COPY SysGese2026/src/main/resources ./src/main/resources

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
COPY --from=build /build/target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]