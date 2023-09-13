FROM maven:3.6.3-jdk-8-slim

WORKDIR /usr/local/app

COPY ./pom.xml ./

RUN mvn clean dependency:go-offline -B
