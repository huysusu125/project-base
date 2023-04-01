FROM openjdk:17

COPY target/*.jar app.jar
EXPOSE 8284
ENTRYPOINT ["java","-jar","/app.jar"]