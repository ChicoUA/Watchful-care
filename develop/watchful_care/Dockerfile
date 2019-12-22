FROM openjdk:11-jdk-stretch

COPY ./target/watchful_care-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch watchful_care-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","watchful_care-0.0.1-SNAPSHOT.jar"]
