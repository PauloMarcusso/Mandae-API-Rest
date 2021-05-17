FROM openjdk:13

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/mandaeapi.jar

EXPOSE 8080

CMD ["java", "-jar", "mandaeapi.jar"]