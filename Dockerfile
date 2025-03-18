# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk

# Set the working directory in the container
WORKDIR /app

LABEL org.opencontainers.image.title="Psicoanalisis Virtual Community" \
      org.opencontainers.image.description="Psicoanalisis Virtual Community Service" \
      org.opencontainers.image.authors="Alexis Mercado"

# Copy the project JAR file into the container at /app
COPY target/PsicoanalisisVirtualCommunity-*.jar app/PsicoanalisisVirtualCommunity.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=local

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app/PsicoanalisisVirtualCommunity.jar"]