FROM amazoncorretto:17 AS jre
WORKDIR /app
COPY build/libs/pix-webhook-solution.jar /app/app.jar
COPY src/main/resources/certificates/truststore.jks /app/truststore.jks
COPY src/main/resources/certificates/keystore.p12 /app/keystore.p12
CMD ["java", "-Dspring.profiles.active=docker", "-Duser.timezone=America/Sao_Paulo", "-jar", "app.jar"]