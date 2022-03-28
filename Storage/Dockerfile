FROM openjdk:11
RUN apt-get update
RUN apt-get install -y wait-for-it
RUN adduser --system --group spring
USER spring:spring
RUN cd /home/spring/
EXPOSE 8083
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /src/app.jar
ENTRYPOINT ["java","-jar","/src/app.jar"]

#docker build -t kompsys/ip .
#docker run -p 8080:8080 kompsys/ip
