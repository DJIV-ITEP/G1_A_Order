FROM openjdk:17-alpine

EXPOSE 8080

ADD target/g1_a_order-*.jar g1_a_order.jar

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://orderdb:5432/order_service 

ENTRYPOINT ["java", "-jar", "/g1_a_order.jar"]