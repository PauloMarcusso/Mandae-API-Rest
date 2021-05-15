FROM openjdk:13

WORKDIR /app

COPY target/*.jar /app/mandaeapi.jar

EXPOSE 8080

CMD ["java", "-jar", "mandaeapi.jar"]