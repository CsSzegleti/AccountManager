# Take-home assignment
## Prerequisites
In order to run the service, you need to have the following:
- JDK 21
- Docker installed
- Docker compose installed
- Dependencies up and running
## Run dependencies
First you need to run the dependent services (database and security check microservice)

```bash
docker compose up -d --build
```

this command will spin up the database and the security microservice

## Start the application
On Linux or Mac:

```bash
./gradlew bootRun
```

On Windows:

```ps
.\gradlew.bat bootRun
```

You can find the openAPI documentation at http://localhost:8080/swagger-ui/index.html