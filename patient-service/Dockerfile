FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# copy entire multi-module project (parent + proto + services)
COPY . .

# build billing-service + proto
RUN mvn -pl patient-service -am clean package -DskipTests

FROM openjdk:21-jdk AS runner
WORKDIR /app

COPY --from=builder /app/patient-service/target/patient-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4000
ENTRYPOINT ["java", "-jar", "app.jar"]
