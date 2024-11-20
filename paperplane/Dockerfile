FROM amazoncorretto:17-al2-jdk AS builder
WORKDIR /paperplane
COPY . .
RUN ./gradlew bootJar

FROM amazoncorretto:17.0.10-alpine
WORKDIR /paperplane
COPY --from=builder /paperplane/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
