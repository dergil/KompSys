FROM openjdk:11
RUN adduser --system --group spring
USER spring:spring
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t kompsys/ip .
#docker run -p 8080:8080 kompsys/ip