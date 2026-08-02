FROM eclipse-temurin:21-jre

LABEL maintainer="Suhail Akhtar"
LABEL service="wallet-service"

WORKDIR /app

COPY target/Wallet-Service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]