FROM node:21 AS ng-builder

RUN npm i -g @angular/cli

WORKDIR /ngapp

COPY pizza-storefront/package*.json .
COPY pizza-storefront/angular.json .
COPY pizza-storefront/tsconfig.* .
COPY pizza-storefront/src src

RUN npm ci && ng build


# Starting with this Linux server
FROM maven:3-eclipse-temurin-21 AS sb-builder

## Build the application
# Create a directory call /sbapp
# go into the directory cd /app
WORKDIR /sbapp

# everything after this is in /sbapp
COPY order-backend/mvnw .
COPY order-backend/mvnw.cmd .
COPY order-backend/pom.xml .
COPY order-backend/.mvn .mvn
COPY order-backend/src src
COPY --from=ng-builder /ngapp/dist/pizza-storefront/ src/main/resources/static

# Build the application
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-bullseye

WORKDIR /app 

COPY --from=sb-builder /sbapp/target/order-backend-0.0.1-SNAPSHOT.jar app.jar

## Run the application
# Define environment variable 
ENV PORT=8080 

# Expose the port
EXPOSE ${PORT}

# Run the program
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar