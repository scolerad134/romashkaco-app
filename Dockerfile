FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/romashkaco-app-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]

EXPOSE 8080