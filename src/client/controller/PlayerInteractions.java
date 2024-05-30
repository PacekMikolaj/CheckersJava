package client.controller;

import server.model.Board;

import java.util.function.Consumer;

public class PlayerInteractions {

    public static int[] selectPawn(Board board, char playerPawn, String input, Consumer<String> sendMessage) {
        int[] selectCoordinates;

        System.out.println("You entered: " + input);
        if (!Board.validateInput(input)) {
            sendMessage.accept("Invalid input. Try again.");
            return new int[]{};
        }
        selectCoordinates = Board.convertMove(input);
        if (!Board.isSelectValid(selectCoordinates, board.boardGrid, playerPawn)) {
            sendMessage.accept("Invalid input. Try again.");
            return new int[]{};
        }
        sendMessage.accept("Select move");
        return selectCoordinates;
    }

    public static int[] selectMove(Board board, char playerPawn, int[] selectCoordinates, String input, Consumer<String> sendMessage) {
        int[] moveCoordinates;

        System.out.println("You entered: " + input);
        if (!Board.validateInput(input)) {
            sendMessage.accept("Invalid input. Try again.");

            return new int[]{};
        }
        moveCoordinates = Board.convertMove(input);

        if (!Board.isMoveValid(moveCoordinates, selectCoordinates, board.boardGrid, playerPawn)) {
            sendMessage.accept("Invalid input. Try again.");

            return new int[]{};
        }
        return moveCoordinates;
    }
}
