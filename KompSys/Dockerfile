
#Workspace initialisation
FROM openjdk:11-jdk as build
WORKDIR /workspaces/app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

#lineendings sind auf linux/windows gestellt und muessen angepasst werden
RUN sed -i 's/\r$//' mvnw

RUN ./mvnw clean install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*jar)
#RUN ./mvnw dependency:go-offline -e -X

FROM openjdk:11-jdk
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT["java","-cp","app:app/lib/*","com.kbe.kompsys.KompSysApplication"]




#RUN adduser --system --group spring
#USER spring:spring
#EXPOSE 8001
#CMD ["./mvnw", "spring-boot:run"]

#ENTRYPOINT ["java","-jar","/app.jar"]

#docker build --tag kompsys:v1.0.0 .
#docker run -p 8080:8080 kompsys/ip
