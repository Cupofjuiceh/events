FROM maven:3.6.1-jdk-8
ADD . /tmp
WORKDIR /tmp
RUN mvn clean package

FROM java:8

COPY --from=0 /tmp/target/events.jar /opt/

ENTRYPOINT ["java","-jar","/opt/events.jar"]

EXPOSE 8080