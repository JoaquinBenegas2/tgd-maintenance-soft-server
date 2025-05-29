envsubst < /app/application-dev.template.properties > /app/application-dev.properties

exec java -Dspring.profiles.active=dev -Dspring.config.additional-location=file:/app/application-dev.properties -jar /app/application.jar
