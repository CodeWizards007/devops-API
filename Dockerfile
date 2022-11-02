FROM openjdk:8-jre-alpine

EXPOSE 8089

ADD target/achat.jar achat.jar
ENTRYPOINT ["java","-jar","/acht.jar"]