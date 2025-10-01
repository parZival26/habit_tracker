# ============================================
# ETAPA 1: BUILD (Compilación)
# ============================================

FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /habit-tracker

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

#ETAPA 2: RUNTIME

FROM eclipse-temurin:17-jre
WORKDIR /habit-tracker

COPY --from=build /habit-tracker/target/*.jar habit-tracker.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "habit-tracker.jar" ]
