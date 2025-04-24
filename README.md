
# Psicoanalisis Virtual Community

This project was generated with java 22 for personal purposes. Currently, I am still working on it and other components will be added in the future.

## Development Configuration

Run with `local` profile. Navigate to `http://localhost:8080/swagger-ui/index.html`. The app will automatically show the swagger url.

## Build

Run `mvn package` to build the project. The build artifacts will be stored in the `target/` directory.

## Running Unit Tests

Run `mvn test` to execute the unit tests.

## Run it with Docker

Download mailhog image with `docker pull mailhog/mailhog`. Run mailhog with `docker run -d -p 8025:8025 -p 1025:1025 mailhog/mailhog`. Navigate to `http://localhost:8025/` to see the mailhog interface.

Create a docker network `docker network create pv-network`.

Execute command `docker run -d --hostname mailhog --network pv-network -p 1025:1025 -p 8025:8025 <mailhog-image-id>`

Execute command `docker run -d --network pv-network -p 8080:8080 <pv-email-image-id>`

Execute command `docker run -d --network pv-network -p 8081:8080 <pv-community-image-id>`