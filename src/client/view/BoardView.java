package client.view;

public class BoardView {
    private char[][] board = new char[8][8];
    private char[] arrayOfLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

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
            returnedBoard.append(arrayOfLetters[i]).append(" ").append(" | ");
            for (int j = 0; j < 8; j++) {
                returnedBoard.append(this.board[i][j]);
                returnedBoard.append(" | ");
            }
            returnedBoard.append("\n");

        }

//        return """
//                  1   2   3   4   5   6   7   8
//              A | o |   | o |   | o |   | o |   |
//              B |   | o |   | o |   | o |   | o |
//              C | o |   | o |   | o |   | o |   |
//              D |   |   |   |   |   |   |   |   |
//              E |   |   |   |   |   |   |   |   |
//              F | x |   | x |   | x |   | x |   |
//              G |   | x |   | x |   | x |   | x |
//              H | x |   | x |   | x |   | x |   |
//              """;
        return returnedBoard.toString();
    }

    ;

};
