FROM openjdk:17-jdk-alpine
COPY ./build/libs/urlshorterapi-0.0.1-SNAPSHOT.jar urlshorterapi.jar
ENTRYPOINT ["java","-jar","/urlshorterapi.jar"]