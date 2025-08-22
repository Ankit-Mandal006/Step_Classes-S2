import java.util.Scanner;

public class CaseConverter {

    // Convert a single character to uppercase using ASCII
    public static char toUpperCaseChar(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return (char)(ch - 32);
        }
        return ch;
    }

    // Convert a single character to lowercase using ASCII
    public static char toLowerCaseChar(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return (char)(ch + 32);
        }
        return ch;
    }

    // Convert entire string to uppercase using ASCII method
    public static String toUpperCase(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toUpperCaseChar(text.charAt(i)));
        }
        return result.toString();
    }

    // Convert entire string to lowercase using ASCII method
    public static String toLowerCase(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toLowerCaseChar(text.charAt(i)));
        }
        return result.toString();
    }

    // Convert string to title case using ASCII methods
    public static String toTitleCase(String text) {
        StringBuilder result = new StringBuilder();
        boolean startOfWord = true;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch == ' ') {
                result.append(ch);
                startOfWord = true;
            } else {
                if (startOfWord) {
                    // Convert first character of word to uppercase
                    result.append(toUpperCaseChar(ch));
                    startOfWord = false;
                } else {
                    // Convert other characters to lowercase
                    result.append(toLowerCaseChar(ch));
                }
            }
        }

        return result.toString();
    }

    // Compare manual conversions with built-in methods
    public static void compareConversions(String text) {
        String manualUpper = toUpperCase(text);
        String manualLower = toLowerCase(text);
        String manualTitle = toTitleCase(text);

        String builtInUpper = text.toUpperCase();
        String builtInLower = text.toLowerCase();

        // Built-in title case doesn't exist in standard String, so we skip that comparison

        System.out.printf("%-20s | %-20s | %-20s | %-20s%n", "Conversion Type", "Manual Result", "Built-in Result", "Match?");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-20s | %-20s | %-20s%n", 
            "Uppercase", manualUpper, builtInUpper, manualUpper.equals(builtInUpper));
        System.out.printf("%-20s | %-20s | %-20s | %-20s%n", 
            "Lowercase", manualLower, builtInLower, manualLower.equals(builtInLower));
        System.out.printf("%-20s | %-20s | %-20s | %-20s%n", 
            "Title Case", manualTitle, "N/A", "N/A");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter text:");
        String inputText = sc.nextLine();

        compareConversions(inputText);

        sc.close();
    }
}
