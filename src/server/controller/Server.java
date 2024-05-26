package server.controller;

import client.controller.PlayerInteractions;
import client.view.BoardView;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import server.model.Board;
import server.model.Constants;
import server.model.Player;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class Server extends WebSocketServer {

    private Board board = new Board();
    private BoardView boardView = new BoardView(board.boardGrid);
    public int playerTurn = 0;

    private int[] selectedCoordinates = {};
    private int[] moveCoordinates;

    public static final int SERVER_PORT = 8080;

    private static ArrayList<Player> players = new ArrayList<>();

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
            webSocket.send("Your Pawn is: %s".formatted(players.isEmpty() ? 'o' : 'x'));
            players.add(new Player( players.isEmpty() ? 'o' : 'x',name, webSocket));
            System.out.println(this.getConnections().toString());
            System.out.println("Connection opened: " + webSocket.getRemoteSocketAddress());
            broadcastAllButSender(webSocket, "%s has joined the chat %s.".formatted(name, webSocket.getRemoteSocketAddress().toString()));
        } else {
            System.out.println("Connection attempt rejected: " + webSocket.getRemoteSocketAddress());
            webSocket.send("Connection rejected. Session already has 2 players.");
            webSocket.close(); // Close the connection if there are already two active connections
        }

        if (players.size() == 2) {
            broadcast("Welcome to Checkers.");
            broadcast(boardView.toString());
            System.out.println(players.toString());
            players.get(playerTurn%2).webSocket.send("Select your pawn :) ...");

        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("Connection closed: " + webSocket.getRemoteSocketAddress());

    }

    @Override
    public void onMessage(WebSocket webSocket, String move) {

        Consumer<String> sendMessage = (message) -> webSocket.send(message);

        Player player = getPlayer(webSocket);

        if (players.size() < 2) {
            webSocket.send("Waiting for another user to connect...");
        } else {

            if (Constants.pawns[playerTurn%2] == player.pawn) {
                System.out.println(move);
                if (selectedCoordinates.length == 0) {
                    selectedCoordinates = PlayerInteractions.selectPawn(board, player.pawn, move, sendMessage);
                } else {
                    moveCoordinates = PlayerInteractions.selectMove(board, player.pawn, selectedCoordinates, move, sendMessage);
                }
                if (selectedCoordinates.length != 0 && moveCoordinates.length != 0) {

                    if (board.boardGrid[moveCoordinates[0]][moveCoordinates[1]] != ' ') {
                        webSocket.send("Nice shot! You've got another move.");
                    } else {
                        playerTurn += 1;
                    }

                    board.updateBoard(selectedCoordinates, moveCoordinates, player.pawn);
                    broadcast(boardView.toString());
                    players.get(playerTurn%2).webSocket.send("Select your pawn :) ...");
                }
            } else {
                webSocket.send("Wait for your turn :)");
            }
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("Error: " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onStart() {
        System.out.println("Server listening on port: " + this.getPort());
    }

    private void broadcastAllButSender(WebSocket webSocket, String message) {
        var connections = new ArrayList<>(this.getConnections());
        connections.remove(webSocket);
        broadcast(message, connections);
    }

    private Player getPlayer(WebSocket webSocket) {
        System.out.println(players.get(0).webSocket.getRemoteSocketAddress().toString());
        System.out.println(webSocket.getRemoteSocketAddress().toString());
        return Objects.equals(players.get(0).webSocket.getRemoteSocketAddress().toString(), webSocket.getRemoteSocketAddress().toString()) ? players.get(0) : players.get(1);
    }

}
