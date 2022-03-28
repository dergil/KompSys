FROM openjdk:11
RUN apt-get update
RUN apt-get install -y wait-for-it
RUN adduser --system --group spring
USER spring:spring
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /src/app.jar
ENTRYPOINT ["java","-jar","/src/app.jar"]
