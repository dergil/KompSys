FROM openjdk:11
RUN adduser --system --group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /src/app.jar
ENTRYPOINT ["java","-jar","/src/app.jar"]
