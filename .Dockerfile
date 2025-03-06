# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the project JAR file into the container at /app
COPY target/PsicoanalisisVirtualCommunity-*.jar app/PsicoanalisisVirtualCommunity.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=local

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app/PsicoanalisisVirtualCommunity.jar"]