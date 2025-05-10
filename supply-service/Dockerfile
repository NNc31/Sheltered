FROM eclipse-temurin:17-jdk
LABEL authors="NNc31"
WORKDIR /app
COPY target/supply-service-1.2-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]