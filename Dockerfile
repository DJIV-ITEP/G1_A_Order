FROM openjdk:17-alpine

EXPOSE 8080

ARG JAR_FILE=target/g1_a_order-1.jar

ADD ${JAR_FILE} g1_a_order.jar

ENTRYPOINT ["java", "-jar", "/g1_a_order.jar"]