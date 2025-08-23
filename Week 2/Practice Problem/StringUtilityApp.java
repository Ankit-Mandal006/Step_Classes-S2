import java.util.*;

public class StringUtilityApp {
    // StringBuilder for efficient output building
    private static StringBuilder output = new StringBuilder();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== STRING UTILITY APPLICATION ===");

        boolean exit = false;
        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Text Analysis");
            System.out.println("2. String Transformation");
            System.out.println("3. ASCII Operations");
            System.out.println("4. Performance Testing");
            System.out.println("5. String Comparison Analysis");
            System.out.println("6. Custom String Algorithms");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter text for analysis: ");
                    String textAnalysis = scanner.nextLine();
                    performTextAnalysis(textAnalysis);
                    displayResults();
                    break;
                case "2":
                    System.out.print("Enter text for transformation: ");
                    String textTransform = scanner.nextLine();
                    System.out.println("Available operations: trim, uppercase, lowercase, replaceSpaceWithUnderscore");
                    System.out.print("Enter operations separated by commas: ");
                    String[] ops = scanner.nextLine().split(",");
                    String transformed = performTransformations(textTransform, ops);
                    System.out.println("Transformed text:\n" + transformed);
                    break;
                case "3":
                    System.out.print("Enter text for ASCII operations: ");
                    String asciiText = scanner.nextLine();
                    performASCIIOperations(asciiText);
                    displayResults();
                    break;
                case "4":
                    System.out.print("Enter number of iterations for performance test: ");
                    int iterations = Integer.parseInt(scanner.nextLine());
                    performPerformanceTest(iterations);
                    break;
                case "5":
                    System.out.print("Enter first string: ");
                    String s1 = scanner.nextLine();
                    System.out.print("Enter second string: ");
                    String s2 = scanner.nextLine();
                    performComparisonAnalysis(new String[]{s1, s2});
                    displayResults();
                    break;
                case "6":
                    System.out.print("Enter text for custom algorithms: ");
                    String customText = scanner.nextLine();
                    performCustomAlgorithms(customText);
                    displayResults();
                    break;
                case "0":
                    exit = true;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    // 1. Text analysis method
    public static void performTextAnalysis(String text) {
        output.setLength(0);
        output.append("=== Text Analysis ===\n");

        int length = text.length();
        output.append("Length: ").append(length).append("\n");

        String[] words = text.trim().split("\\s+");
        output.append("Word count: ").append(words.length).append("\n");

        int sentences = text.split("[.!?]+").length;
        output.append("Sentence count: ").append(sentences).append("\n");

        int paragraphs = text.split("\\n+").length;
        output.append("Paragraph count: ").append(paragraphs).append("\n");

        // Character frequency
        Map<Character, Integer> freq = new HashMap<>();
        for (char ch : text.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                freq.put(ch, freq.getOrDefault(ch, 0) + 1);
            }
        }
        output.append("Character frequency:\n");
        freq.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(e -> output.append(" '").append(e.getKey()).append("' : ").append(e.getValue()).append("\n"));

        // Most common words
        Map<String, Integer> wordFreq = new HashMap<>();
        for (String w : words) {
            if (!w.isEmpty()) {
                wordFreq.put(w.toLowerCase(), wordFreq.getOrDefault(w.toLowerCase(), 0) + 1);
            }
        }
        output.append("Most common words:\n");
        wordFreq.entrySet().stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .limit(5)
            .forEach(e -> output.append(" ").append(e.getKey()).append(" : ").append(e.getValue()).append("\n"));
    }

    // 2. String transformation method
    public static String performTransformations(String text, String[] operations) {
        StringBuilder sb = new StringBuilder(text);
        for (String op : operations) {
            op = op.trim().toLowerCase();
            switch (op) {
                case "trim":
                    sb = new StringBuilder(sb.toString().trim());
                    break;
                case "uppercase":
                    sb = new StringBuilder(sb.toString().toUpperCase());
                    break;
                case "lowercase":
                    sb = new StringBuilder(sb.toString().toLowerCase());
                    break;
                case "replacespacewithunderscore":
                    sb = new StringBuilder(sb.toString().replace(' ', '_'));
                    break;
                default:
                    // ignore unknown operation
            }
        }
        return sb.toString();
    }

    // 3. ASCII operations
    public static void performASCIIOperations(String text) {
        output.setLength(0);
        output.append("=== ASCII Operations ===\n");
        output.append("Character | ASCII | Type\n");
        for (char ch : text.toCharArray()) {
            output.append("   '").append(ch).append("'   |  ").append((int) ch).append("  | ").append(classifyCharacter(ch)).append("\n");
        }
        output.append("\nCaesar cipher (+3 shift):\n");
        output.append(caesarCipher(text, 3));
    }

    private static String classifyCharacter(char ch) {
        if (ch >= 'A' && ch <= 'Z') return "Uppercase Letter";
        if (ch >= 'a' && ch <= 'z') return "Lowercase Letter";
        if (ch >= '0' && ch <= '9') return "Digit";
        return "Special Character";
    }

    private static String caesarCipher(String text, int shift) {
        StringBuilder cipher = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int offset = (ch - base + shift) % 26;
                cipher.append((char) (base + offset));
            } else {
                cipher.append(ch);
            }
        }
        return cipher.toString();
    }

    // 4. Performance testing for String, StringBuilder, StringBuffer
    public static void performPerformanceTest(int iterations) {
        System.out.println("=== Performance Testing ===");
        long start, end;

        // String concatenation
        start = System.nanoTime();
        String s = "";
        for (int i = 0; i < iterations; i++) {
            s += i;
        }
        end = System.nanoTime();
        System.out.println("String concat: " + (end - start) + " ns");

        // StringBuilder
        start = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(i);
        }
        String resultSB = sb.toString();
        end = System.nanoTime();
        System.out.println("StringBuilder append: " + (end - start) + " ns");

        // StringBuffer
        start = System.nanoTime();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sbf.append(i);
        }
        String resultSBF = sbf.toString();
        end = System.nanoTime();
        System.out.println("StringBuffer append: " + (end - start) + " ns");
    }

    // 5. String comparison analysis
    public static void performComparisonAnalysis(String[] strings) {
        output.setLength(0);
        output.append("=== String Comparison Analysis ===\n");
        String s1 = strings[0], s2 = strings[1];

        output.append("Reference equality (==): ").append(s1 == s2).append("\n");
        output.append("Content equality (equals): ").append(s1.equals(s2)).append("\n");
        output.append("Case-insensitive equality (equalsIgnoreCase): ").append(s1.equalsIgnoreCase(s2)).append("\n");
        output.append("Lexicographic compareTo: ").append(s1.compareTo(s2)).append("\n");
        output.append("Case-insensitive compareToIgnoreCase: ").append(s1.compareToIgnoreCase(s2)).append("\n");
    }

    // 6. Custom algorithms: palindrome check and anagram detection
    public static void performCustomAlgorithms(String text) {
        output.setLength(0);
        output.append("=== Custom Algorithms ===\n");

        output.append("Is palindrome? ").append(isPalindrome(text) ? "Yes" : "No").append("\n");

        System.out.print("Enter another string to check for anagram: ");
        String other = scanner.nextLine();
        output.append("Is anagram with '").append(other).append("'? ").append(isAnagram(text, other) ? "Yes" : "No").append("\n");
    }

    private static boolean isPalindrome(String text) {
        String clean = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int len = clean.length();
        for (int i = 0; i < len / 2; i++) {
            if (clean.charAt(i) != clean.charAt(len - 1 - i)) return false;
        }
        return true;
    }

    private static boolean isAnagram(String s1, String s2) {
        char[] a1 = s1.replaceAll("\\s+", "").toLowerCase().toCharArray();
        char[] a2 = s2.replaceAll("\\s+", "").toLowerCase().toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    // Display output accumulated in output StringBuilder
    public static void displayResults() {
        System.out.println(output.toString());
        output.setLength(0);
    }
}
