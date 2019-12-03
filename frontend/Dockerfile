FROM maven:3.6-jdk-11 as build
COPY src /usr/src/frontend/src
COPY pom.xml /usr/src/frontend
RUN mvn -f /usr/src/frontend/pom.xml clean package -Dmaven.test.skip package

FROM gcr.io/distroless/java:11
COPY --from=build /usr/src/frontend/target/frontend-0.0.1-SNAPSHOT.jar /usr/frontend/frontend-0.0.1-SNAPSHOT.jar
EXPOSE 8100
ENTRYPOINT [ "java", "-jar", "/usr/frontend/frontend-0.0.1-SNAPSHOT.jar" ]