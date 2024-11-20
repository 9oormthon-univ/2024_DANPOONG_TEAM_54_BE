FROM amazoncorretto:17-al2-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM amazoncorretto:17.0.10-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
