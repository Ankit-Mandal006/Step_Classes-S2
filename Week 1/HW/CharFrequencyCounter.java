import java.util.Scanner;

public class CharFrequencyCounter {
    
    // Method to find character frequencies
    public static String[] findCharacterFrequencies(String text) {
        char[] chars = text.toCharArray();               // Convert text to char array
        int[] freq = new int[chars.length];              // Array to store frequency
        String[] result;                                 // 1D array to return results

        for (int i = 0; i < chars.length; i++) {
            freq[i] = 1; // Initialize frequency

            // Skip already counted characters
            if (chars[i] == '0') {
                continue;
            }

            // Nested loop to find duplicates
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    freq[i]++;
                    chars[j] = '0'; // Mark as counted
                }
            }
        }

        // Count non-zero characters to size the result array
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '0') {
                count++;
            }
        }

        result = new String[count];
        int index = 0;

        // Populate result array with character-frequency strings
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '0') {
                result[index] = chars[i] + " - " + freq[i];
                index++;
            }
        }

        return result;
    }

    // Main function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        // Call method
        String[] frequencies = findCharacterFrequencies(input);

        // Display result
        System.out.println("\nCharacter Frequencies:");
        for (String s : frequencies) {
            System.out.println(s);
        }

        scanner.close();
    }
}
