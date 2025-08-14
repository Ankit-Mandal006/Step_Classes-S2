import java.util.Scanner;

public class WordsAndLengths {

    // Method to find length without using length()
    public static int findLength(String str) {
        int count = 0;
        try {
            while (true) {
                str.charAt(count);
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            // end of string
        }
        return count;
    }

    // Method to split text into words without using split()
    public static String[] customSplit(String text) {
        int length = findLength(text);
        if (length == 0) {
            return new String[0];
        }

        // Count spaces to find number of words
        int spaceCount = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == ' ') {
                spaceCount++;
            }
        }
        int wordCount = spaceCount + 1;

        // Record space indexes
        int[] spaceIndexes = new int[spaceCount];
        int idx = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == ' ') {
                spaceIndexes[idx++] = i;
            }
        }

        // Extract words using substring
        String[] words = new String[wordCount];
        int start = 0;
        for (int i = 0; i < spaceCount; i++) {
            words[i] = substring(text, start, spaceIndexes[i]);
            start = spaceIndexes[i] + 1;
        }
        words[wordCount - 1] = substring(text, start, length);

        return words;
    }

    // Helper substring method
    public static String substring(String str, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    // Create 2D array: each row is [word, length as String]
    public static String[][] wordsWithLengths(String[] words) {
        String[][] result = new String[words.length][2];
        for (int i = 0; i < words.length; i++) {
            int len = findLength(words[i]);
            result[i][0] = words[i];
            result[i][1] = String.valueOf(len); // convert int to String
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a line of text: ");
        String input = scanner.nextLine();

        String[] words = customSplit(input);
        String[][] wordsAndLengths = wordsWithLengths(words);

        System.out.println("\nWord\t\tLength");
        System.out.println("------------------------");
        for (String[] pair : wordsAndLengths) {
            String word = pair[0];
            int length = Integer.parseInt(pair[1]);  // convert String to int for display
            System.out.printf("%-15s %d\n", word, length);
        }

        scanner.close();
    }
}
