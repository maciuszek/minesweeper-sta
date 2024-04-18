### Description

Naive minesweeper implementation over Spring MVC

### Dependencies
* jdk17
* Postman (https://www.postman.com/)

### Build The App As A Jar
`./mvnw -U clean package`

### Run The Jar
`java -Dlogging.level.com.maciuszek.minesweeper=DEBUG -jar target/minesweeper-*-SNAPSHOT.jar`

### Play The Game
Go to `http://localhost:8080`

### Play The Game Over API
Import `Minesweeper.postman_collection.json` into Postman and use the requests
