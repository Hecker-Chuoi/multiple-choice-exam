FROM amazoncorretto:23-jdk AS build

WORKDIR /app
COPY /target/exam-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]