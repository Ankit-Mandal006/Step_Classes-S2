import java.util.Scanner;

public class CharacterFrequency {

    // a. Method to find character frequencies using ASCII values and charAt()
    public static String[][] findCharFrequencies(String text) {
        int[] freq = new int[256];  // Array to store frequencies for all ASCII characters

        // i & ii. Count frequency using charAt()
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            freq[ch]++;
        }

        // iii. Count unique characters for result array size
        int uniqueCount = 0;
        for (int i = 0; i < text.length(); i++) {
            if (freq[text.charAt(i)] != 0) {
                uniqueCount++;
                freq[text.charAt(i)] = 0; // Mark as counted
            }
        }

        // iv. Prepare result array
        String[][] result = new String[uniqueCount][2];
        freq = new int[256]; // Reset freq array for reuse

        // Recount for final result
        for (int i = 0; i < text.length(); i++) {
            freq[text.charAt(i)]++;
        }

        boolean[] visited = new boolean[256];
        int index = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (!visited[ch]) {
                result[index][0] = String.valueOf(ch);
                result[index][1] = String.valueOf(freq[ch]);
                visited[ch] = true;
                index++;
            }
        }

        return result;
    }

    // Method to display result in tabular format
    public static void displayFrequencies(String[][] data) {
        System.out.printf("%-10s | %-10s%n", "Character", "Frequency");
        System.out.println("--------------------------");
        for (String[] row : data) {
            System.out.printf("%-10s | %-10s%n", row[0], row[1]);
        }
    }

    // b. Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = sc.nextLine();

        String[][] frequencies = findCharFrequencies(input);
        displayFrequencies(frequencies);

        sc.close();
    }
}
