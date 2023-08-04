FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} wanted-0.0.1-SNAPSHOT.jar
ENV    PROFILE dev
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "wanted-0.0.1-SNAPSHOT.jar"]