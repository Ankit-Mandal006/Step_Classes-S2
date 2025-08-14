import java.util.Scanner;

public class FrequencyWithUniqueChars {

    // a. Method to find unique characters using charAt() and nested loops
    public static char[] findUniqueCharacters(String text) {
        int len = text.length();
        char[] temp = new char[len]; // Max possible unique chars
        int uniqueCount = 0;

        for (int i = 0; i < len; i++) {
            char current = text.charAt(i);
            boolean isUnique = true;

            // Check if current character is already in temp
            for (int j = 0; j < uniqueCount; j++) {
                if (temp[j] == current) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                temp[uniqueCount] = current;
                uniqueCount++;
            }
        }

        // Create result array of only unique characters
        char[] uniqueChars = new char[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            uniqueChars[i] = temp[i];
        }

        return uniqueChars;
    }

    // b. Method to calculate frequencies and return 2D array
    public static String[][] findCharFrequencies(String text) {
        int[] freq = new int[256];  // Frequency array for ASCII characters

        // i & ii. Count frequency using ASCII index
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            freq[ch]++;
        }

        // iii. Get unique characters
        char[] uniqueChars = findUniqueCharacters(text);

        // iv. Create 2D String array for result
        String[][] result = new String[uniqueChars.length][2];

        // v. Fill the 2D array with character and its frequency
        for (int i = 0; i < uniqueChars.length; i++) {
            result[i][0] = String.valueOf(uniqueChars[i]);
            result[i][1] = String.valueOf(freq[uniqueChars[i]]);
        }

        return result;
    }

    // Method to display the result in tabular format
    public static void displayFrequencies(String[][] data) {
        System.out.printf("%-10s | %-10s%n", "Character", "Frequency");
        System.out.println("----------------------------");
        for (String[] row : data) {
            System.out.printf("%-10s | %-10s%n", row[0], row[1]);
        }
    }

    // c. Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = sc.nextLine();

        String[][] frequencies = findCharFrequencies(input);
        displayFrequencies(frequencies);

        sc.close();
    }
}
