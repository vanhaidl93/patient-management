FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy pom.xml and prefetch dependencies (cache layer) in batch mode, no interactive prompts.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy source code and build the service.
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk AS runner
WORKDIR /app

COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4004
ENTRYPOINT ["java", "-jar", "app.jar"]
