package server.model;

public class Board {
    public char[][] boardGrid = new char[8][8];
    public static final char[] pawns = new char[]{'o', 'x'};

    public static char getOponnentsPawn(char pawn) {
        return pawn == pawns[0] ? 'x' : 'o';
    }

    public Board() {
        this.boardGrid = new char[][]{
                {'o', ' ', 'o', ' ', 'o', ' ', 'o', ' '},
                {' ', 'o', ' ', 'o', ' ', 'o', ' ', 'o'},
                {'o', ' ', 'o', ' ', 'o', ' ', 'o', ' '},
                {' ', 'x', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'x', ' ', 'x', ' ', 'x', ' ', 'x', ' '},
                {' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x'},
                {'x', ' ', 'x', ' ', 'x', ' ', 'x', ' '}
        };
    }


    static public boolean validateInput(String input) {
        input = input.trim();
        input = input.toUpperCase();

        if (input.equals("Q")) return true;
        if (input.length() != 2) return false;


        char firstChar = input.charAt(0);
        char secondChar = input.charAt(1);

        if (Constants.findCharInArray(Constants.arrayOfLetters, firstChar) == -1) return false;
        if (
                !(Character.isDigit(secondChar) &&
                        Character.getNumericValue(secondChar) >= 1
                        && Character.getNumericValue(secondChar) <= 8)
        ) return false;


        return true;
    }

    static public int[] convertMove(String move) {
        move = move.toUpperCase();
        int x = Constants.findCharInArray(Constants.arrayOfLetters, move.charAt(0));
        int y = Character.getNumericValue(move.charAt(1)) - 1;

        return new int[]{x, y};

    }


    public void updateBoard(int[] selectedCoordinates, int[] moveCoordinates, char playerPawn) {
        int[] moveDirections = getMoveDirections(selectedCoordinates, moveCoordinates);

        boardGrid[selectedCoordinates[0]][selectedCoordinates[1]] = ' ';

        if (boardGrid[moveCoordinates[0]][moveCoordinates[1]] == ' ') {
            boardGrid[moveCoordinates[0]][moveCoordinates[1]] = playerPawn;
        } else {
            boardGrid[moveCoordinates[0]][moveCoordinates[1]] = ' ';
            boardGrid[moveCoordinates[0] + moveDirections[0]][moveCoordinates[1] + moveDirections[1]] = playerPawn;
        }
    }

    static public boolean checkIfInBoardDimentions(int[] coordinates) {
        return (coordinates[0] >= 0 && coordinates[0] < 8 && coordinates[1] >= 0 && coordinates[1] < 8);
    }

    static public boolean isSelectValid(int[] coordinates, char[][] boardGrid, char playerPawn) {
        if (boardGrid[coordinates[0]][coordinates[1]] != playerPawn) return false;
        for (int[] move : Constants.listOfMoves) {
            int[] moveCoordinates = {coordinates[0] + move[0], coordinates[1] + move[1]};
            if (isMoveValid(moveCoordinates, coordinates, boardGrid, playerPawn)) return true;
        }
        return false;
    }

    static public boolean isMoveValid(int[] moveCoordinates, int[] selectCoordinates, char[][] boardGrid, char playerPawn) {
        int[] moveDirections = getMoveDirections(selectCoordinates, moveCoordinates);
        if (!checkIfInBoardDimentions(moveCoordinates)) return false;
        if (Math.abs(moveDirections[0]) != 1 || Math.abs(moveDirections[1]) != 1) return false;
        if (boardGrid[moveCoordinates[0]][moveCoordinates[1]] == playerPawn) return false;
        if (boardGrid[moveCoordinates[0]][moveCoordinates[1]] != ' ') {
            int[] newCoordinates = new int[]{
                    (moveCoordinates[0] + moveDirections[0]),
                    (moveCoordinates[1] + moveDirections[1])
            };

            if (!checkIfInBoardDimentions(newCoordinates)) return false;
            if (boardGrid[newCoordinates[0]][newCoordinates[1]] != ' ') return false;

        }

        return true;
    }

    static int[] getMoveDirections(int[] selectCoordinates, int[] moveCoordinates) {
        return new int[]{
                (moveCoordinates[0] - selectCoordinates[0]),
                (moveCoordinates[1] - selectCoordinates[1])
        };
    }

}
