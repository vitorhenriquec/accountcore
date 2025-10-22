# **Accounts**

## Prerequisites
- Java 21
- Gradle 8.14.3
- Docker
- Docker compose

## Run

### Databse

To run this project you initialy have to run the docker-compose file and set up the postresql local database. For that use this following command:

```shell
docker compose up -d
```

### Gradle

Then you've got to run clean build the project. For that use the following command:

```shell
gradle clean build
```

### Docker
Then you cant step up the application using Docker with this command:

```shell
docker build -t accounts-app .
docker run -p 8082:8082 -e SPRING_PROFILES_ACTIVE=local accounts-app
```




