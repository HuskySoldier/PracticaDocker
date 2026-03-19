# Etapa de construcción
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
# Damos permisos de ejecución al wrapper de Gradle y compilamos
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]