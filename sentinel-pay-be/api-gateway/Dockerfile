FROM eclipse-temurin:21-jre

LABEL maintainer="Suhail Akhtar"
LABEL service="api-gateway"

WORKDIR /app

COPY target/Api-Gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]