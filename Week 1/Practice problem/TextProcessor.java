import java.util.*;

public class TextProcessor {

    // Method to clean and validate input: trim spaces, remove extra spaces, proper case
    public static String cleanInput(String input) {
        // Remove leading/trailing spaces and multiple spaces between words
        String cleaned = input.trim().replaceAll("\\s+", " ");
        // Convert to proper case: capitalize first letter of each word
        String[] words = cleaned.split(" ");
        StringBuilder properCase = new StringBuilder();
        for (String w : words) {
            if (w.length() > 0) {
                properCase.append(Character.toUpperCase(w.charAt(0)))
                          .append(w.substring(1).toLowerCase())
                          .append(" ");
            }
        }
        return properCase.toString().trim();
    }

    // Analyze text: count words, sentences, characters; find longest word, most common character
    public static void analyzeText(String text) {
        // Count words
        String[] words = text.split("\\s+");
        int wordCount = words.length;

        // Count sentences (using ., !, ? as delimiters)
        String[] sentences = text.split("[.!?]+");
        int sentenceCount = sentences.length;

        // Count characters excluding spaces
        int charCount = text.replace(" ", "").length();

        // Find longest word
        String longestWord = "";
        for (String w : words) {
            // Remove punctuation from word edges
            String cleanWord = w.replaceAll("\\p{Punct}", "");
            if (cleanWord.length() > longestWord.length()) {
                longestWord = cleanWord;
            }
        }

        // Find most common character (letters only, case-insensitive)
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
            }
        }
        char mostCommonChar = ' ';
        int maxFreq = 0;
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                mostCommonChar = entry.getKey();
            }
        }

        // Display statistics
        System.out.println("\n--- Text Analysis ---");
        System.out.println("Words: " + wordCount);
        System.out.println("Sentences: " + sentenceCount);
        System.out.println("Characters (excluding spaces): " + charCount);
        System.out.println("Longest word: " + longestWord);
        System.out.println("Most common character: '" + mostCommonChar + "' appeared " + maxFreq + " times");
    }

    // Create word array, remove punctuation, sort alphabetically
    public static String[] getWordsSorted(String text) {
        String[] rawWords = text.split("\\s+");
        List<String> cleanedWords = new ArrayList<>();
        for (String w : rawWords) {
            String cleanWord = w.replaceAll("\\p{Punct}", "").toLowerCase();
            if (!cleanWord.isEmpty()) {
                cleanedWords.add(cleanWord);
            }
        }
        Collections.sort(cleanedWords);
        return cleanedWords.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TEXT PROCESSOR ===");
        System.out.print("Enter a paragraph of text:\n");
        String input = scanner.nextLine();

        // 1. Clean and validate input
        String cleanedText = cleanInput(input);
        System.out.println("\nCleaned Text:\n" + cleanedText);

        // 2. Analyze text
        analyzeText(cleanedText);

        // 3. Show words in alphabetical order
        String[] sortedWords = getWordsSorted(cleanedText);
        System.out.println("\nWords in alphabetical order:");
        for (String w : sortedWords) {
            System.out.println(w);
        }

        // 4. Allow user to search for specific words
        System.out.print("\nEnter a word to search (or press Enter to skip): ");
        String searchWord = scanner.nextLine().toLowerCase();

        if (!searchWord.isEmpty()) {
            int occurrences = 0;
            for (String w : sortedWords) {
                if (w.equals(searchWord)) {
                    occurrences++;
                }
            }
            if (occurrences > 0) {
                System.out.println("The word '" + searchWord + "' appears " + occurrences + " time(s).");
            } else {
                System.out.println("The word '" + searchWord + "' was not found.");
            }
        } else {
            System.out.println("No search performed.");
        }

        scanner.close();
    }
}
