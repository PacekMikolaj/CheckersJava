package server.controller;

import client.view.BoardView;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import server.model.Board;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server extends WebSocketServer {

    private Board board = new Board();
    private BoardView boardView = new BoardView(board.boardGrid);

    public static final int SERVER_PORT = 8080;

    private static Map<String, Object[]> players = new HashMap<>();

    public Server() {
        super(new InetSocketAddress(SERVER_PORT));
    }

    public static void main(String[] args) {
        var server = new Server();
        server.start();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        if (players.size() < 2) {
            var resource = webSocket.getResourceDescriptor();
            String name = resource.split("=")[1];
            players.put(webSocket.getRemoteSocketAddress().toString(), new Object[]{players.isEmpty() ? 'o' : 'x'});

            System.out.println("Connection opened: " + webSocket.getRemoteSocketAddress());
            broadcastAllButSender(webSocket, "%s has joined the chat %s.".formatted(name, webSocket.getRemoteSocketAddress().toString()));
        } else {
            System.out.println("Connection attempt rejected: " + webSocket.getRemoteSocketAddress());
            webSocket.send("Connection rejected. Session already has 2 players.");
            webSocket.close(); // Close the connection if there are already two active connections
        }

        if(players.size() == 2) {
            System.out.println(players.get(webSocket.getRemoteSocketAddress()));
            broadcast("Welcome to Checkers.");
            broadcast(boardView.toString());
            System.out.println(players.toString());
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("Connection closed: " + webSocket.getRemoteSocketAddress());

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
//        if (activeConnections < 2) {
            webSocket.send("Waiting for another user to connect...");
//        } else {

//            String chatName = players.get(webSocket.getRemoteSocketAddress().toString())[0];
//            broadcastAllButSender(webSocket, "%s: %s".formatted(chatName, s));
//        }

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("Error: " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onStart() {
        System.out.println("Server listining on port: " + this.getPort());
    }

    private void broadcastAllButSender(WebSocket webSocket, String message) {
        var connections = new ArrayList<>(this.getConnections());
        connections.remove(webSocket);
        broadcast(message, connections);
    }
}
