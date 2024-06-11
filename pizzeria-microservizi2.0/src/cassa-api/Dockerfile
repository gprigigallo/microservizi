FROM amazoncorretto:11-alpine-jdk

ARG JAR_FILE=target/cassa-api-0.0.2.jar

COPY ${JAR_FILE} cassa.jar
ENTRYPOINT ["java","-jar","/cassa.jar"]