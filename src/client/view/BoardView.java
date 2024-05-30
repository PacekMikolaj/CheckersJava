package client.view;

import server.model.Constants;

public class BoardView {
    private char[][] board = new char[8][8];

    public BoardView(char[][] board) {
        this.board = board;
    }

    @Override
    public String toString() {
        StringBuilder returnedBoard = new StringBuilder("    ");
        for (int i = 0; i < 8; i++) {
            returnedBoard.append(" ").append(i + 1).append("  ");
        }
        returnedBoard.append("\n");
        for (int i = 0; i < 8; i++) {
            returnedBoard.append(Constants.arrayOfLetters[i]).append(" ").append(" | ");
            for (int j = 0; j < 8; j++) {
                returnedBoard.append(this.board[i][j]);
                returnedBoard.append(" | ");
            }
            returnedBoard.append("\n");

        }
        return returnedBoard.toString();
    }

    ;
};
