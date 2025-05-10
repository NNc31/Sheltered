FROM eclipse-temurin:17-jdk
LABEL authors="NNc31"
WORKDIR /app
COPY target/api-gateway-1.2-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]