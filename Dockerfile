FROM maven:3.8.3-amazoncorretto-17 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine
COPY --from=MAVEN_BUILD /build/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]