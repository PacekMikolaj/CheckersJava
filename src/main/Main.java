package main;

import client.controller.PlayerInteractions;
import client.view.BoardView;
import server.model.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
         BoardView boardView = new BoardView(board.boardGrid);
         System.out.println(boardView);
            int[][] move = PlayerInteractions.makeMove(board, 'o');
            System.out.println(move.toString());
//         board.updateBoard("H1");

    }
}