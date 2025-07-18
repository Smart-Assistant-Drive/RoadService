FROM eclipse-temurin:21-jdk-alpine
LABEL authors="Daniele"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]