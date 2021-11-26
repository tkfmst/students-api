FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=./api-0.2.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
