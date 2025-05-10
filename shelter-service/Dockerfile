FROM eclipse-temurin:17-jdk
LABEL authors="NNc31"
WORKDIR /app
COPY target/shelter-service-1.2-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]