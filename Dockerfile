FROM nadjim/maven-jdk-11:version1 AS java-builder

WORKDIR /usr/src/app

COPY ./pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn clean install

FROM openjdk:11.0-jre as java-runtime

COPY --from=java-builder /usr/src/app/target/*.jar /app.jar

CMD ["java", "-Xmx50m", "-jar", "/app.jar", "--spring.profiles.active=docker"]

EXPOSE 5000