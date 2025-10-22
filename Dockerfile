FROM eclipse-temurin:21-jdk-alpine

COPY build/libs/*.jar /app.jar

EXPOSE 8082
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar", "/app.jar"]