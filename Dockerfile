FROM openjdk:17-alpine

EXPOSE 8080

ARG JAR_FILE=target/g1_a_order-1.jar

ADD ${JAR_FILE} g1_a_order.jar

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://orderdb:5432/order_service 

ENTRYPOINT ["java", "-jar", "/g1_a_order.jar"]