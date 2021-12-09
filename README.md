# Battleship!

## About:

   Battleship is a strategy-guessing game for 2 players. Each player, after placing their 5 ships on their own board, take turns guessing the coordinates of the other player's ships. If all the sections of a ship have been hit, that ship is sunk. The player who sinks all of the other player's ships first is the winner. 
     
   This version of Battleship was written using Java 17, Gradle 7.3, and SpringBoot 2.5.6. It is meant to be the backend of a future Battleship website. 
     
## Installation:
    
- Java is necessary to run this program. You can find instructions on how to install it [here.](https://java.com/en/download/help/download_options.html)

- This program uses the Java 17 SDK.

- You will also need to install Gradle. Please follow instructions [here](https://docs.gradle.org/current/userguide/installation.html) to download Gradle 7.3.

- Git clone this repo into a folder of your choice. After navigating to the correct folder in your terminal, type `./gradlew run` to start the program. 

- [Postman](https://www.postman.com/) can be used to send the HTTP requests without a front end. 


## How to Play:

- After installing the program, CD into the root directory and type `./gradlew run` to start the server. 
- Switch over to Postman (or Curl) and send the following requests: 
     - GET: 'localhost:8080/'  - will return the Welcome message in JSON. 
     - GET: 'localhost:8080/boards; - will return the current state of both Boards. 
     - PATCH: 'localhost:8080/board/place' - will place a ship on the player board at the coordinates included in the body. The body of the request should look like this:     
        ``` 
        {
          "ship": {
            "name": "test",
            "width": 2,
            "height": 1,
            "isSunk": false,
            "shipSections": [
              {
                "isHit": false,
                "isShip": true,
                "shipName": "test"
              },
              {
                "isHit": false,
                "isShip": true,
                "shipName": "test"
              }
            ]
          },
          "row": 0,
          "col": 0
        }
        ```
  - PATCH: 'localhost:8080/board/hit' - will hit the given location on the opponants board. The body of the request should look like this:
 
        {
        "row": 0, 
        "col": 0
        }

