FROM maven:3.6-jdk-11 as build
COPY src /usr/src/financial/src
COPY pom.xml /usr/src/financial
RUN mvn -f /usr/src/financial/pom.xml clean package -Dmaven.test.skip package

FROM gcr.io/distroless/java:11
COPY --from=build /usr/src/financial/target/financial-0.0.1-SNAPSHOT.jar /usr/financial/financial-0.0.1-SNAPSHOT.jar
EXPOSE 8100
ENTRYPOINT [ "java", "-jar", "/usr/financial/financial-0.0.1-SNAPSHOT.jar" ]