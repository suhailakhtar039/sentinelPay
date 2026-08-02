FROM eclipse-temurin:21-jre

LABEL maintainer="Suhail Akhtar"
LABEL service="payment-service"

WORKDIR /app

COPY target/paymentservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","app.jar"]