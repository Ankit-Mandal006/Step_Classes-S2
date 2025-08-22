import java.util.ArrayList;
import java.util.Scanner;

public class SubstringReplace {

    // Method to find all occurrences of 'sub' in 'text'
    public static ArrayList<Integer> findOccurrences(String text, String sub) {
        ArrayList<Integer> positions = new ArrayList<>();
        int index = 0;
        while (true) {
            index = text.indexOf(sub, index);
            if (index == -1)
                break;
            positions.add(index);
            index += sub.length(); // Move past this occurrence for next search
        }
        return positions;
    }

    // Method to manually replace all occurrences of 'sub' with 'replacement' in
    // 'text'
    public static String manualReplace(String text, String sub, String replacement) {
        ArrayList<Integer> positions = findOccurrences(text, sub);
        if (positions.isEmpty())
            return text; // No occurrences found

        StringBuilder result = new StringBuilder();
        int textIndex = 0; // Index to track position in original text
        int posIndex = 0; // Index to track occurrences positions

        while (textIndex < text.length()) {
            // If current position matches an occurrence, insert replacement
            if (posIndex < positions.size() && textIndex == positions.get(posIndex)) {
                // Append replacement string
                for (int i = 0; i < replacement.length(); i++) {
                    result.append(replacement.charAt(i));
                }
                textIndex += sub.length(); // Skip original substring chars
                posIndex++;
            } else {
                // Append original character
                result.append(text.charAt(textIndex));
                textIndex++;
            }
        }

        return result.toString();
    }

    // Method to compare manual replacement with built-in replace
    public static boolean compareWithBuiltIn(String text, String sub, String replacement) {
        String manualResult = manualReplace(text, sub, replacement);
        String builtInResult = text.replace(sub, replacement);
        return manualResult.equals(builtInResult);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the main text:");
        String text = sc.nextLine();

        System.out.println("Enter the substring to find:");
        String sub = sc.nextLine();

        System.out.println("Enter the replacement substring:");
        String replacement = sc.nextLine();

        String manualResult = manualReplace(text, sub, replacement);
        String builtInResult = text.replace(sub, replacement);
        boolean comparison = compareWithBuiltIn(text, sub, replacement);

        System.out.println("\nResult after manual replacement:");
        System.out.println(manualResult);

        System.out.println("\nResult after built-in replace:");
        System.out.println(builtInResult);

        System.out.println("\nDo both methods produce the same result? " + comparison);

        sc.close();
    }
}
