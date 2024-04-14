### Description

Naive minesweeper implementation over a REST API

### Dependencies
* jdk17
* Postman (https://www.postman.com/)

### Build The App As A Jar
`./mvnw -U clean package`

### Run The Jar
`java -Dlogging.level.com.maciuszek.minesweeper=DEBUG -jar target/minesweeper-*-SNAPSHOT.jar`

### Play The Game
Import `Minesweeper.postman_collection.json` into Postman and use the requests to play the game

### Todo
Also implement Spring MVC interface
