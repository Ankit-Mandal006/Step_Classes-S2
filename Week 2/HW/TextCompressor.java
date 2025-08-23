import java.util.*;

public class TextCompressor {

    // Method b: Count character frequency without HashMap
    public static Object[] countFrequencies(String text) {
        List<Character> charList = new ArrayList<>();
        List<Integer> freqList = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int index = charList.indexOf(c);
            if (index == -1) {
                charList.add(c);
                freqList.add(1);
            } else {
                freqList.set(index, freqList.get(index) + 1);
            }
        }

        char[] chars = new char[charList.size()];
        int[] freqs = new int[freqList.size()];
        for (int i = 0; i < charList.size(); i++) {
            chars[i] = charList.get(i);
            freqs[i] = freqList.get(i);
        }

        return new Object[]{chars, freqs};
    }

    // Method c: Generate compression codes
    public static String[][] generateCodes(char[] chars, int[] freqs) {
        int n = chars.length;
        String[][] codeMap = new String[n][2];

        // Sort by frequency (descending)
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (freqs[j] > freqs[i]) {
                    // Swap frequencies
                    int tempFreq = freqs[i];
                    freqs[i] = freqs[j];
                    freqs[j] = tempFreq;
                    // Swap characters
                    char tempChar = chars[i];
                    chars[i] = chars[j];
                    chars[j] = tempChar;
                }
            }
        }

        // Assign short codes (1-2 characters) to most frequent
        String[] shortCodes = {
                "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
                "a", "b", "c", "d", "e", "f", "g", "h"
        };

        for (int i = 0; i < n; i++) {
            codeMap[i][0] = String.valueOf(chars[i]);
            codeMap[i][1] = i < shortCodes.length ? shortCodes[i] : "z" + i; // fallback
        }

        return codeMap;
    }

    // Method d: Compress text
    public static String compressText(String text, String[][] codeMap) {
        StringBuilder compressed = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            String ch = String.valueOf(text.charAt(i));
            for (int j = 0; j < codeMap.length; j++) {
                if (codeMap[j][0].equals(ch)) {
                    compressed.append(codeMap[j][1]);
                    break;
                }
            }
        }

        return compressed.toString();
    }

    // Method e: Decompress text
    public static String decompressText(String compressed, String[][] codeMap) {
        StringBuilder decompressed = new StringBuilder();

        int i = 0;
        while (i < compressed.length()) {
            boolean matched = false;

            // Try 2-char match first
            for (int j = 0; j < codeMap.length; j++) {
                String code = codeMap[j][1];
                int len = code.length();

                if (i + len <= compressed.length() && compressed.substring(i, i + len).equals(code)) {
                    decompressed.append(codeMap[j][0]);
                    i += len;
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                decompressed.append('?'); // unknown code
                i++;
            }
        }

        return decompressed.toString();
    }

    // Method f: Display analysis
    public static void displayAnalysis(String original, String compressed, String decompressed,
                                       char[] chars, int[] freqs, String[][] codeMap) {
        System.out.println("\nCharacter Frequency Table:");
        System.out.printf("%-10s %-10s %-10s\n", "Char", "Freq", "Code");
        System.out.println("--------------------------------");
        for (int i = 0; i < chars.length; i++) {
            System.out.printf("%-10s %-10d %-10s\n", (chars[i] == ' ' ? "[space]" : chars[i]), freqs[i], codeMap[i][1]);
        }

        System.out.println("\nOriginal Text:\n" + original);
        System.out.println("\nCompressed Text:\n" + compressed);
        System.out.println("\nDecompressed Text:\n" + decompressed);

        int originalSize = original.length();
        int compressedSize = compressed.length();
        double efficiency = ((originalSize - compressedSize) * 100.0) / originalSize;

        System.out.printf("\nOriginal Size: %d chars\n", originalSize);
        System.out.printf("Compressed Size: %d chars\n", compressedSize);
        System.out.printf("Compression Efficiency: %.2f%%\n", efficiency);

        if (original.equals(decompressed)) {
            System.out.println("\n✅ Decompression successful (text matches original)");
        } else {
            System.out.println("\n❌ Decompression failed (text does not match original)");
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text to compress:");
        String input = scanner.nextLine();
        scanner.close();

        // Count frequency
        Object[] freqResult = countFrequencies(input);
        char[] chars = (char[]) freqResult[0];
        int[] freqs = (int[]) freqResult[1];

        // Generate code map
        String[][] codeMap = generateCodes(chars, freqs);

        // Compress
        String compressed = compressText(input, codeMap);

        // Decompress
        String decompressed = decompressText(compressed, codeMap);

        // Display analysis
        displayAnalysis(input, compressed, decompressed, chars, freqs, codeMap);
    }
}
