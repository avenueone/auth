# auth
This is a microservice for authentication, authorization, session management and orchestration for UI.

## Dependencies
This service depends on:
 1. PostGres. ENV Variable: POSTGRES_URL
 2. Planning Svc: ENV Variable: PLANNING_SVC_URL
 3. Target Svc: ENV Variable: TARGET_SVC_URL

## Development

To start your application local:

``` 
mvn clean install 
java -jar -POSTGRES_URL=localhost:5432 authSvc/target/auth-svc-*.jar
```
*** Please change localhost and port with the postgres db url

Note: If you are running in IDE, please add ENV variable `POSTGRES_URL`

The service will start on port 8950. To check if application is started clean, run the below command:

```curl -X GET http://localhost:8950/auth-svc/api/health```


## Create Docker

To build docker image run:


``` 
mvn clean install 
mvn verify -P docker
 ```

This will create a docker image with repository as `registry.avenueone.com/avo/auth-svc` and `$VERSION` as tag.


## Run using Docker Compose
If you want to run both Postgres and Auth Svc using docker compose, run the below command

``` 
docker-compose -f docker/docker-compose.yml  up -d
```
The application will be  started on port 8950. To check if application is started clean, run the below command:

```curl -X GET http://localhost:8950/management/health```

## Run standalone docker

To run the docker image locally:

```docker run -p 8950:8950 -e POSTGRES_URL=localhost:5432 -d registry.avenueone.com/avo/auth-svc:0.0.1-SNAPSHOT ```

The application will be  started on port 9080. To check if application is started clean, run the below command:

```curl -X GET http://localhost:8950/management/health```

## Testing

Coming soon..

### Code quality

Coming soon..

## Continuous Integration (optional)

Coming soon..

## Production

Coming soon..
