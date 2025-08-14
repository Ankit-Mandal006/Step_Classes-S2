import java.util.Scanner;

public class ShortestLongestWords {

    // Method to find length without using length()
    public static int findLength(String str) {
        int count = 0;
        try {
            while (true) {
                str.charAt(count);
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            // End of string reached
        }
        return count;
    }

    // Method to split text into words without using split()
    public static String[] customSplit(String text) {
        int length = findLength(text);
        if (length == 0) return new String[0];

        // Count spaces to determine number of words
        int spaceCount = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == ' ') spaceCount++;
        }
        int wordCount = spaceCount + 1;

        // Store indexes of spaces
        int[] spaceIndexes = new int[spaceCount];
        int idx = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == ' ') {
                spaceIndexes[idx++] = i;
            }
        }

        // Extract words using substring helper
        String[] words = new String[wordCount];
        int start = 0;
        for (int i = 0; i < spaceCount; i++) {
            words[i] = substring(text, start, spaceIndexes[i]);
            start = spaceIndexes[i] + 1;
        }
        words[wordCount - 1] = substring(text, start, length);

        return words;
    }

    // Helper substring method (start inclusive, end exclusive)
    public static String substring(String str, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    // Create 2D array [word, length as String]
    public static String[][] wordsWithLengths(String[] words) {
        String[][] result = new String[words.length][2];
        for (int i = 0; i < words.length; i++) {
            int len = findLength(words[i]);
            result[i][0] = words[i];
            result[i][1] = String.valueOf(len);
        }
        return result;
    }

    // Find shortest and longest words by length in 2D array
    // Returns int array: [indexOfShortest, indexOfLongest]
    public static int[] findShortestAndLongest(String[][] wordsWithLengths) {
        if (wordsWithLengths.length == 0) return new int[]{-1, -1};

        int shortestIndex = 0;
        int longestIndex = 0;

        int shortestLength = Integer.parseInt(wordsWithLengths[0][1]);
        int longestLength = Integer.parseInt(wordsWithLengths[0][1]);

        for (int i = 1; i < wordsWithLengths.length; i++) {
            int currentLength = Integer.parseInt(wordsWithLengths[i][1]);
            if (currentLength < shortestLength) {
                shortestLength = currentLength;
                shortestIndex = i;
            }
            if (currentLength > longestLength) {
                longestLength = currentLength;
                longestIndex = i;
            }
        }
        return new int[]{shortestIndex, longestIndex};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a line of text: ");
        String input = scanner.nextLine();

        // Split words without split()
        String[] words = customSplit(input);

        // Create 2D array of words and lengths
        String[][] wordsAndLengths = wordsWithLengths(words);

        // Find shortest and longest words
        int[] shortestAndLongest = findShortestAndLongest(wordsAndLengths);

        // Display results
        if (wordsAndLengths.length == 0) {
            System.out.println("No words entered.");
        } else {
            int shortestIndex = shortestAndLongest[0];
            int longestIndex = shortestAndLongest[1];

            System.out.println("\nShortest word: '" + wordsAndLengths[shortestIndex][0] + 
                               "' with length " + wordsAndLengths[shortestIndex][1]);

            System.out.println("Longest word: '" + wordsAndLengths[longestIndex][0] + 
                               "' with length " + wordsAndLengths[longestIndex][1]);
        }

        scanner.close();
    }
}
