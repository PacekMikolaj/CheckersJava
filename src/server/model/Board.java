package server.model;

import java.util.Arrays;

public class Board {
    public char[][] board = new char[8][8];

    public Board() {
        this.board = new char[][]{
                {'o', ' ', 'o', ' ', 'o', ' ', 'o', ' '},
                {' ', 'o', ' ', 'o', ' ', 'o', ' ', 'o'},
                {'o', ' ', 'o', ' ', 'o', ' ', 'o', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
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
        int x = Constants.findCharInArray(Constants.arrayOfLetters,move.charAt(0));
        int y = Character.getNumericValue(move.charAt(1));

        return new int[]{x, y};

    }

    public void updateBoard(String move) {
        if (validateInput(move)) {
            int[] coordinates = convertMove(move);
            System.out.println("halo");
            System.out.println(Arrays.toString(coordinates));
        } else {
            System.out.println("Error");
        }
    }
}
