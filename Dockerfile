FROM amazoncorretto:17-alpine-jdk

EXPOSE 8082
ADD ./build/libs/user-service-0.0.1-SNAPSHOT.jar user-service.jar

ENTRYPOINT ["java", "-jar", "user-service.jar"]