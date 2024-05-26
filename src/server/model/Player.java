package server.model;

import org.java_websocket.WebSocket;


public class Player {
    public char pawn;
    public String name;
    public WebSocket webSocket;

    public Player(char pawn, String name, WebSocket webSocket) {
        this.pawn = pawn;
        this.name = name;
        this.webSocket = webSocket;
    }
}
