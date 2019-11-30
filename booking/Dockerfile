FROM maven:3.6-jdk-11 as build
COPY src /usr/src/booking/src
COPY pom.xml /usr/src/booking
RUN mvn -f /usr/src/booking/pom.xml clean package -Dmaven.test.skip package

FROM gcr.io/distroless/java:11
COPY --from=build /usr/src/booking/target/booking-0.0.1-SNAPSHOT.jar /usr/booking/booking-0.0.1-SNAPSHOT.jar
EXPOSE 8100
ENTRYPOINT [ "java", "-jar", "/usr/booking/booking-0.0.1-SNAPSHOT.jar" ]