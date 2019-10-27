FROM java:8
EXPOSE 8027
ADD /target/Gateway-1.0.0.jar Gateway-1.0.0.jar
ENTRYPOINT ["java", "-Deureka.client.serviceUrl.defaultZone=http://192.168.15.34:8023/eureka", "-Dspring.rabbitmq.host=192.168.15.34", "-jar","Gateway-1.0.0.jar"]