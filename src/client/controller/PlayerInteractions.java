package client.controller;

import server.model.Board;

import java.util.Scanner;

public class PlayerInteractions {

    public static int[][] makeMove (Board board, char playerPawn) {
        int[] moveCoordinates;
        int[] selectCoordinates;
        Scanner scanner = new Scanner(System.in);
        while(true) {
        System.out.println("Which pawn to move?");
        String input = scanner.nextLine();
        System.out.println("You entered: " + input);
        if(Board.validateInput(input)) {

            selectCoordinates = Board.convertMove(input);
            if(Board.isSelectValid(selectCoordinates,board.boardGrid,'o')){
            break;
            }

        }
        System.out.println("Wrong input. Try again.");
        }

        while(true) {
            System.out.println("Where to move pawn?");
            String input = scanner.nextLine();
            System.out.println("You entered: " + input);
            if(Board.validateInput(input)) {
                moveCoordinates = Board.convertMove(input);

                if(Board.isMoveValid(selectCoordinates, moveCoordinates, board.boardGrid, 'o')){
                    break;
                }
            }
            System.out.println("Wrong input. Try again.");
        }

        scanner.close();



        return new int[][]{selectCoordinates,moveCoordinates};
    }
}
