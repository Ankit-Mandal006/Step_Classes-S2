import java.util.Scanner;

public class AdvancedStringAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== ADVANCED STRING ANALYZER ===");

        System.out.print("Enter first string: ");
        String str1 = scanner.nextLine();

        System.out.print("Enter second string: ");
        String str2 = scanner.nextLine();

        performAllComparisons(str1, str2);

        double similarity = calculateSimilarity(str1, str2);
        System.out.printf("Similarity percentage: %.2f%%\n", similarity * 100);

        analyzeMemoryUsage(str1, str2);

        String[] inputs = {str1, str2};
        String optimizedResult = optimizedStringProcessing(inputs);
        System.out.println("Optimized concatenation result: " + optimizedResult);

        demonstrateStringIntern();

        scanner.close();
    }

    // Calculate similarity using Levenshtein distance algorithm
    public static double calculateSimilarity(String str1, String str2) {
        int distance = levenshteinDistance(str1, str2);
        int maxLen = Math.max(str1.length(), str2.length());
        if (maxLen == 0) return 1.0; // both empty strings
        return 1.0 - ((double) distance / maxLen);
    }

    private static int levenshteinDistance(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) dp[i][0] = i;
        for (int j = 0; j <= len2; j++) dp[0][j] = j;

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (s1.charAt(i-1) == s2.charAt(j-1)) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i-1][j] + 1, dp[i][j-1] + 1),
                        dp[i-1][j-1] + cost
                );
            }
        }
        return dp[len1][len2];
    }

    // Perform all string comparison types and print results
    public static void performAllComparisons(String str1, String str2) {
        System.out.println("\n--- Comparison Results ---");
        System.out.println("Reference equality (==): " + (str1 == str2));
        System.out.println("Content equality (equals): " + str1.equals(str2));
        System.out.println("Case-insensitive equality (equalsIgnoreCase): " + str1.equalsIgnoreCase(str2));
        System.out.println("Lexicographic comparison (compareTo): " + str1.compareTo(str2));
        System.out.println("Case-insensitive compareToIgnoreCase: " + str1.compareToIgnoreCase(str2));
    }

    // Approximate memory usage of strings
    public static void analyzeMemoryUsage(String... strings) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Request garbage collection to get clean baseline

        long before = runtime.totalMemory() - runtime.freeMemory();

        // Dummy operation to ensure strings are in use
        int totalLength = 0;
        for (String s : strings) {
            totalLength += s.length();
        }

        long after = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("Approximate memory usage for given strings: %d bytes\n", (after - before));
    }

    // Use StringBuilder to concatenate multiple strings efficiently
    public static String optimizedStringProcessing(String[] inputs) {
        StringBuilder sb = new StringBuilder();
        for (String s : inputs) {
            sb.append(s).append(" | ");
        }
        if (sb.length() > 3)
            sb.setLength(sb.length() - 3); // remove last separator
        return sb.toString();
    }

    // Demonstrate intern() method to show string pool behavior
    public static void demonstrateStringIntern() {
        System.out.println("\n--- String Intern Demonstration ---");
        String a = new String("hello");
        String b = "hello";
        System.out.println("Before intern(): a == b ? " + (a == b));
        String c = a.intern();
        System.out.println("After intern(): a.intern() == b ? " + (c == b));
    }
}
