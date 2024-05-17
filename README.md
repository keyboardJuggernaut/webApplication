# Software engineering final project
This repository includes a spring boot application implemented for the final project of software engineer course of study.

## Requirements
1. openjdk >= 21.0.2
2. mysql

## Configuration
Some environmental variables are required:
1. in 'application.properties' set:
    * spring.datasource.url : database url
    * spring.datasource.username : database username
    * spring.datasource.password : database password
    * jakarta.persistence.jdbc.url : database url 
    * parking.area.order : the order of the matrix representing parking area
    * parking.area.name : the name of the parking area
    * jwt.secret : the jwt secret to sign
    * jwt.expiration : the jwt expiration time

## Build & run
After running the dbms, follow these steps: 
1. package the app
   ```
   mvnw package
   ```
2. Run from JAR file created
   ```
   java -jar target\webApplication-0.0.1-SNAPSHOT.jar
   ```
   
Or use maven plugin:
   ```
   mvnw spring-boot:run
   ```

## Build & run with docker
After compiling and creating a .jar file of the project
   ```
   docker build --build-arg JAR_FILE=target/*.jar -t myorg/myapp .
   ```
followed by
   ```
   docker run -p 8080:8080 myorg/myapp
   ```