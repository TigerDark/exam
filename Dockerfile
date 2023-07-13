FROM openjdk:8-jdk-alpine
WORKDIR /exam1
ARG JAR_FILE=/my-jar/*.jar
COPY ${JAR_FILE} /exam1-0.0.1-SNAPSHOT-plain.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Xmx3G", "-jar", "/exam1-0.0.1-SNAPSHOT-plain.jar"]
