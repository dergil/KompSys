FROM openjdk:11
RUN apt-get update
RUN apt-get install -y wait-for-it
RUN adduser --system --group spring
USER spring:spring
RUN mkdir /home/spring/csv
RUN touch /home/spring/csv/file.txt
RUN cd /home/spring/
RUN mkdir /home/spring/csv2
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t kompsys/ip .
#docker run -p 8080:8080 kompsys/ip