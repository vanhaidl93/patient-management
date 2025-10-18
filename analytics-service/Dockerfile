FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# copy entire multi-module project (parent + proto + analytics-service)
# need to run on root folder, patient-manager and using a temperary analytics-parent-pom.xml for build in docker)
COPY analytics-parent-pom.xml ./pom.xml
COPY proto ./proto
COPY analytics-service ./analytics-service

# build billing-service + proto
RUN mvn -pl analytics-service -am clean package -DskipTests

FROM openjdk:21-jdk AS runner
WORKDIR /app

COPY --from=builder /app/analytics-service/target/analytics-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4002
ENTRYPOINT ["java", "-jar", "app.jar"]