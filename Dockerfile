FROM gradle:8.8.0-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootjar

FROM openjdk:21-jdk-slim

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/ /app/

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/o2o-server-0.0.1-SNAPSHOT.jar"]
