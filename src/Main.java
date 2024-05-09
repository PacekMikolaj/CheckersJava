import client.view.BoardView;

public class Main {
    public static void main(String[] args) {
        char[][] board = {
                {'o', ' ', 'o', ' ', 'o', ' ', 'o', ' '},
                {' ', 'o', ' ', 'o', ' ', 'o', ' ', 'o'},
                {'o', ' ', 'o', ' ', 'o', ' ', 'o', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'x', ' ', 'x', ' ', 'x', ' ', 'x', ' '},
                {' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x'},
                {'x', ' ', 'x', ' ', 'x', ' ', 'x', ' '}
        };

        BoardView boardView = new BoardView(board);
        System.out.println(boardView);
    }
}
