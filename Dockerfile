FROM gradle:8-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew :app:webApp:wasmJsBrowserDistribution :server:copyWebDist :server:shadowJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/server/build/libs/*-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx256m", "-jar", "app.jar"]
