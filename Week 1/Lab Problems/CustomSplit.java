import java.util.Scanner;

public class CustomSplit {

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
        // 1. Count number of words by counting spaces + 1 (handle empty string)
        int length = findLength(text);
        if (length == 0) {
            return new String[0];
        }

        int spaceCount = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == ' ') {
                spaceCount++;
            }
        }
        int wordCount = spaceCount + 1;

        // 2. Store indexes of spaces in an array
        int[] spaceIndexes = new int[spaceCount];
        int index = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == ' ') {
                spaceIndexes[index++] = i;
            }
        }

        // 3. Extract words using space indexes
        String[] words = new String[wordCount];
        int start = 0;
        for (int i = 0; i < spaceCount; i++) {
            words[i] = substring(text, start, spaceIndexes[i]);
            start = spaceIndexes[i] + 1;
        }
        // Last word (from last space to end)
        words[wordCount - 1] = substring(text, start, length);

        return words;
    }

    // Helper substring method since we can't use built-in substring()
    // Extract substring from start (inclusive) to end (exclusive)
    public static String substring(String str, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    // Method to compare two string arrays for equality
    public static boolean compareStringArrays(String[] arr1, String[] arr2) {
        if (arr1 == null && arr2 == null) return true;
        if (arr1 == null || arr2 == null) return false;
        if (arr1.length != arr2.length) return false;

        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a line of text: ");
        String input = scanner.nextLine();

        // Use custom method to split
        String[] customWords = customSplit(input);

        // Use built-in split method (split by space)
        String[] builtInWords = input.split(" ");

        // Compare results
        boolean areEqual = compareStringArrays(customWords, builtInWords);

        // Display results
        System.out.println("\nCustom split words:");
        for (String w : customWords) {
            System.out.println(w);
        }

        System.out.println("\nBuilt-in split words:");
        for (String w : builtInWords) {
            System.out.println(w);
        }

        System.out.println("\nAre the two arrays equal? " + areEqual);

        scanner.close();
    }
}
