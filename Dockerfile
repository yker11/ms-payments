FROM openjdk:11.0-oracle
VOLUME /tmp
EXPOSE 8096
ADD ./target/payments-service-0.0.1-SNAPSHOT.jar payments-service.jar
ENTRYPOINT ["java","-jar","/payments-service.jar"]