FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# copy entire multi-module project (parent + proto + appointment-service)
# need to run on root folder, patient-manager folder,
# using a temperary apointment-parent-pom.xml for building.
COPY appointment-parent-pom.xml ./pom.xml
COPY proto ./proto
COPY appointment-service ./appointment-service

# build billing-service + proto
RUN mvn -pl appointment-service -am clean package -DskipTests

FROM openjdk:21-jdk AS runner
WORKDIR /app

COPY --from=builder /app/appointment-service/target/appointment-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4006
ENTRYPOINT ["java", "-jar", "app.jar"]
