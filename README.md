# CheckersJava

This is a Java implementation of the classic board game, Checkers.

## Structure

The project is divided into two main parts: the client and the server. The project is based on websockets. When two players are connected to the server, the game starts.

### Client

The client is responsible for interacting with the user and displaying the game state. It is implemented in the following files:

- [`Client.java`](src/client/controller/Client.java)
- [`PlayerInteractions.java`](src/client/controller/PlayerInteractions.java)
- [`BoardView.java`](src/client/view/BoardView.java)

### Server

The server is responsible for managing the game logic. It is implemented in the following files:

- [`Server.java`](src/server/controller/Server.java)
- [`Board.java`](src/server/model/Board.java)
- [`Player.java`](src/server/model/Player.java)
- [`Constants.java`](src/server/model/Constants.java)

## Building the Project

The project uses Java 17. To build the project, follow the instructions in your IDE for building Java projects.

## License

This project is licensed under the terms of the MIT license.
