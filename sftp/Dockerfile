FROM openjdk:11
RUN apt-get update && apt-get install -y openssh-server
RUN mkdir /var/run/sshd
# Password & Authentication
RUN echo 'root:mypassword123' | chpasswd
RUN sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# SSH & Keeping Session Alive
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

ENV NOTVISIBLE "in users profile"
RUN echo "export VISIBLE=now" >> /etc/profile

EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]

#EXPOSE 8081
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t kompsys/ip .
#docker run -p 8080:8080 kompsys/ip