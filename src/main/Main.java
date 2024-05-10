package main;

import client.view.BoardView;
import server.model.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
         BoardView boardView = new BoardView(board.board);
         System.out.println(boardView);
         board.updateBoard("H1");
    }
}