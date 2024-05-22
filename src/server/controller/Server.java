package server.controller;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server extends WebSocketServer {

    public static final int SERVER_PORT = 8080;

    private static Map<String, String> map = new HashMap<>();
    private int activeConnections = 0;

    public Server() {
        super(new InetSocketAddress(SERVER_PORT));
    }

    public static void main(String[] args) {
        var server = new Server();
        server.start();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        if (activeConnections < 2) {
            var resource = webSocket.getResourceDescriptor();
            String name = resource.split("=")[1];
            map.put(webSocket.getRemoteSocketAddress().toString(), name);
            activeConnections++; // Increment the counter when a new connection is opened
            System.out.println("Connection opened: " + webSocket.getRemoteSocketAddress());
            broadcastAllButSender(webSocket, "%s has joined the chat %s.".formatted(name, webSocket.getRemoteSocketAddress().toString()));
        } else {
            System.out.println("Connection attempt rejected: " + webSocket.getRemoteSocketAddress());
            webSocket.send("Connection rejected. Session already has 2 players.");
            webSocket.close(); // Close the connection if there are already two active connections
        }
            broadcast("Connected users: %d".formatted(activeConnections));
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        activeConnections--;
        System.out.println("Connection closed: " + webSocket.getRemoteSocketAddress());

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        if (activeConnections < 2) {
            webSocket.send("Waiting for another user to connect...");
        } else {
            String chatName = map.get(webSocket.getRemoteSocketAddress().toString());
            broadcastAllButSender(webSocket, "%s: %s".formatted(chatName, s));
        }

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
