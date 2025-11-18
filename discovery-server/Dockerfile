FROM maven:3.9-eclipse-temurin-21 AS builder

# project -> run
WORKDIR /build

COPY pom.xml ./

RUN mvn -DskipTests dependency:purge-local-repository

COPY . .

RUN mvn -U clean package -DskipTests

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

COPY --from=builder /build/target/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=docker"]