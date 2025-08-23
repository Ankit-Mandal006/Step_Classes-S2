import java.util.*;

public class StringManipulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a sentence with mixed formatting:");
        String input = scanner.nextLine();

        System.out.println("\n=== ORIGINAL INPUT ===");
        System.out.println(input);

        String trimmed = input.trim();
        String underscored = trimmed.replace(" ", "_");
        String noDigits = trimmed.replaceAll("\\d", "");
        String[] words = trimmed.split("\\s+");
        String rejoined = String.join(" | ", words);

        System.out.println("\n=== BASIC STRING MANIPULATIONS ===");
        System.out.println("Trimmed: " + trimmed);
        System.out.println("Spaces replaced with '_': " + underscored);
        System.out.println("Digits removed: " + noDigits);
        System.out.println("Words array: " + Arrays.toString(words));
        System.out.println("Joined with '|': " + rejoined);

        String noPunctuation = removePunctuation(trimmed);
        String capitalized = capitalizeWords(trimmed);
        String reversed = reverseWordOrder(trimmed);

        System.out.println("\n=== ADVANCED PROCESSING ===");
        System.out.println("Without punctuation: " + noPunctuation);
        System.out.println("Capitalized: " + capitalized);
        System.out.println("Reversed word order: " + reversed);

        System.out.println("\n=== WORD FREQUENCY ===");
        countWordFrequency(trimmed);

        scanner.close();
    }

    public static String removePunctuation(String text) {
        return text.replaceAll("[\\p{Punct}]", "");
    }

    public static String capitalizeWords(String text) {
        String[] words = text.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1)).append(" ");
            }
        }
        return result.toString().trim();
    }

    public static String reverseWordOrder(String text) {
        String[] words = text.trim().split("\\s+");
        StringBuilder reversed = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            reversed.append(words[i]).append(" ");
        }
        return reversed.toString().trim();
    }

    public static void countWordFrequency(String text) {
        String cleaned = text.toLowerCase().replaceAll("[^a-zA-Z ]", "");
        String[] words = cleaned.split("\\s+");
        Map<String, Integer> freqMap = new LinkedHashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            System.out.printf("'%s' : %d\n", entry.getKey(), entry.getValue());
        }
    }
}
