FROM openjdk:23-jdk


ARG JAR_FILE=target/knack.dataintegration.service-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
ENV DD_SERVICE=rule-utils-service
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

