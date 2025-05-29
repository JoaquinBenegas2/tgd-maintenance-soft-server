# Build Stage
FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Final Stage with Minimal JDK
FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache bash gettext

COPY --from=build /target/*.jar /app/application.jar
COPY src/main/resources/application-dev.template.properties /app/application-dev.template.properties
COPY entrypoint.sh /app/entrypoint.sh

WORKDIR /app
RUN chmod +x /app/entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/app/entrypoint.sh"]
