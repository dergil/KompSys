FROM openjdk:11
RUN adduser --system --group spring
USER spring:spring
RUN mkdir /home/spring/csv
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
