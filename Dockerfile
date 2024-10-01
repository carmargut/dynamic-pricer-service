FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY target/dynamic-pricer-service-0.0.1-SNAPSHOT.jar /app/dynamic-pricer-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/dynamic-pricer-service.jar"]