import java.util.*;

public class SimpleSpellChecker {

    // Method b: Split sentence into words without split()
    public static List<String> splitWords(String sentence) {
        List<String> words = new ArrayList<>();
        int start = -1;
        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                if (start == -1) start = i;
            } else {
                if (start != -1) {
                    words.add(sentence.substring(start, i));
                    start = -1;
                }
            }
        }
        if (start != -1) {
            words.add(sentence.substring(start));
        }
        return words;
    }

    // Method c: Calculate string distance (simple Levenshtein-like)
    public static int stringDistance(String a, String b) {
        int[][] dp = new int[a.length()+1][b.length()+1];
        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i-1) == b.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    int insert = dp[i][j-1] + 1;
                    int delete = dp[i-1][j] + 1;
                    int replace = dp[i-1][j-1] + 1;
                    dp[i][j] = Math.min(insert, Math.min(delete, replace));
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    // Method d: Suggest correction from dictionary
    public static String suggestCorrection(String word, String[] dictionary) {
        int minDist = Integer.MAX_VALUE;
        String suggestion = word;

        for (String dictWord : dictionary) {
            int dist = stringDistance(word.toLowerCase(), dictWord.toLowerCase());
            if (dist < minDist) {
                minDist = dist;
                suggestion = dictWord;
            }
        }

        return (minDist <= 2) ? suggestion : word;
    }

    // Method e: Display spell check results
    public static void displayResults(List<String> words, String[] dictionary) {
        System.out.printf("\n%-15s %-20s %-10s %-15s\n", "Word", "Suggestion", "Distance", "Status");
        System.out.println("-------------------------------------------------------------");

        for (String word : words) {
            String suggestion = suggestCorrection(word, dictionary);
            int dist = stringDistance(word.toLowerCase(), suggestion.toLowerCase());
            String status = word.equalsIgnoreCase(suggestion) ? "Correct" : "Misspelled";
            System.out.printf("%-15s %-20s %-10d %-15s\n", word, suggestion, dist, status);
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample dictionary (can be extended)
        String[] dictionary = {
            "hello", "world", "this", "is", "a", "simple", "spell", "checker",
            "example", "sentence", "java", "program", "input", "output"
        };

        // Input
        System.out.println("Enter a sentence to spell check:");
        String inputSentence = scanner.nextLine();

        scanner.close();

        List<String> words = splitWords(inputSentence);
        displayResults(words, dictionary);
    }
}
