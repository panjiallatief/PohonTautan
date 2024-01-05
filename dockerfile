FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/taut.jar 
COPY src/main/resources/application.yml /app/application.yml
WORKDIR /app
CMD ["java","-jar","taut.jar"]