FROM openjdk:13

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/mandaeapi.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "mandaeapi.jar"]