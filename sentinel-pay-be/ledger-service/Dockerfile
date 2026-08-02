FROM eclipse-temurin:21-jre

LABEL maintainer="Suhail Akhtar"
LABEL service="ledger-service"

WORKDIR /app

COPY target/ledger-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java","-jar","app.jar"]