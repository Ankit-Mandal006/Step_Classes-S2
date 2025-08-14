import java.util.Scanner;

public class CharTypeFinder {

    // a. Method to check character type: Vowel, Consonant, or Not a Letter
    public static String checkCharType(char ch) {
        // Convert uppercase to lowercase using ASCII
        if (ch >= 'A' && ch <= 'Z') {
            ch = (char)(ch + 32);
        }
        
        // Check if ch is a letter
        if (ch >= 'a' && ch <= 'z') {
            // Check vowels
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                return "Vowel";
            } else {
                return "Consonant";
            }
        }
        
        return "Not a Letter";
    }

    // b. Method to return 2D array of [character, type]
    public static String[][] findCharTypes(String text) {
        int length = text.length();
        String[][] result = new String[length][2];
        
        for (int i = 0; i < length; i++) {
            char ch = text.charAt(i);
            result[i][0] = String.valueOf(ch);
            result[i][1] = checkCharType(ch);
        }
        
        return result;
    }

    // c. Method to display 2D array in tabular format
    public static void displayTable(String[][] data) {
        System.out.printf("%-10s | %-15s%n", "Character", "Type");
        System.out.println("-----------------------------");
        for (String[] row : data) {
            System.out.printf("%-10s | %-15s%n", row[0], row[1]);
        }
    }

    // d. main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = sc.nextLine();

        String[][] charTypes = findCharTypes(input);
        displayTable(charTypes);

        sc.close();
    }
}
