FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/spring-boot-rest-2-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar