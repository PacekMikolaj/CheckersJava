package server.model;

public class Constants {
    public static final char[] arrayOfLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    public static int findCharInArray(char[] array, char target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1; // Return a default value if the target is not found
    }
}
