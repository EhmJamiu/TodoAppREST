FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/learn-0.0.1-SNAPSHOT.jar /app/todo.jar

EXPOSE 8080

CMD ["java", "-jar", "todo.jar"]

#LABEL authors="PC"
