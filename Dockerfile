FROM eclipse-temurin:21

EXPOSE 8080

ARG JAR_FILE=target/*.jar

WORKDIR /tmp/app

COPY ${JAR_FILE} app.jar

CMD ["java", "-jar", "app.jar"]