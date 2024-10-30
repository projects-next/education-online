# Education-online-project
The education platform for whole students in the worlds.

## Technologies
- Spring Boot
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Swagger (Open API)
- Docker
- Liquibase

## Run the Application

First you need to make sure that the database is up.
If you're using Docker, you can use ```docker compose up -d``` command. (If you have made changes in local, you should use the *local-docker-compose* file.)

Navigate to the root of the project. For building the project using command line, run below command :

``` ./gradlew clean build -x test```

Run service in command line. Navigate to *target* directory.

``` java -jar education-0.0.1-SNAPSHOT.jar ```

## Postman Collection

- [You can access the Postman collection here and you can try it after you get the project up and running.](https://www.postman.com/education-online/workspace/education-online)


### License

MIT License
