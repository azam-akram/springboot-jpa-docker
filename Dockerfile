FROM eclipse-temurin:19-jdk-alpine

EXPOSE 8080

ADD build/libs/springboot-jpa-docker-0.0.1-SNAPSHOT.jar /app/springboot-jpa-docker.jar

WORKDIR /app/

CMD ["java","-jar","springboot-jpa-docker.jar"]