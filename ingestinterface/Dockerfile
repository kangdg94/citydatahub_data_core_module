FROM eclipse-temurin:11
WORKDIR /
ADD target/ingest-interface-2.0.1.jar app.jar
ADD keystore.p12 keystore.p12
ENTRYPOINT [ "java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Djava.net.preferIPv4Stack=true", \
    "-Dspring.profiles.active=local", \
    "-jar", \
    "app.jar"]
