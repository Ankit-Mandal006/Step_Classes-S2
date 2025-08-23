import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFormatter {

    // Method a + b: Split input text into words manually
    public static List<String> splitWords(String text) {
        List<String> words = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (start != i) {
                    words.add(text.substring(start, i));
                }
                start = i + 1;
            }
        }
        if (start < text.length()) {
            words.add(text.substring(start));
        }
        return words;
    }

    // Method c: Justify text using StringBuilder
    public static List<String> justifyText(List<String> words, int width) {
        List<String> result = new ArrayList<>();
        int index = 0;

        while (index < words.size()) {
            int lineLen = words.get(index).length();
            int last = index + 1;

            while (last < words.size()) {
                if (lineLen + 1 + words.get(last).length() > width) break;
                lineLen += 1 + words.get(last).length();
                last++;
            }

            int spaceSlots = last - index - 1;
            StringBuilder line = new StringBuilder();

            if (last == words.size() || spaceSlots == 0) {
                for (int i = index; i < last; i++) {
                    line.append(words.get(i));
                    if (i < last - 1) line.append(" ");
                }
                while (line.length() < width) line.append(" ");
            } else {
                int totalSpaces = width - lineLen + spaceSlots;
                int spacePerSlot = totalSpaces / spaceSlots;
                int extra = totalSpaces % spaceSlots;

                for (int i = index; i < last; i++) {
                    line.append(words.get(i));
                    if (i < last - 1) {
                        for (int s = 0; s < spacePerSlot + (i - index < extra ? 1 : 0); s++) {
                            line.append(" ");
                        }
                    }
                }
            }

            result.add(line.toString());
            index = last;
        }
        return result;
    }

    // Method d: Center-align each line
    public static List<String> centerAlign(List<String> lines, int width) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            int padding = (width - line.trim().length()) / 2;
            StringBuilder centered = new StringBuilder();
            for (int i = 0; i < padding; i++) centered.append(" ");
            centered.append(line.trim());
            result.add(centered.toString());
        }
        return result;
    }

    // Method e: Performance comparison
    public static long justifyWithStringConcat(List<String> words, int width) {
        int index = 0;
        long start = System.nanoTime();
        while (index < words.size()) {
            int lineLen = words.get(index).length();
            int last = index + 1;

            while (last < words.size()) {
                if (lineLen + 1 + words.get(last).length() > width) break;
                lineLen += 1 + words.get(last).length();
                last++;
            }

            int spaceSlots = last - index - 1;
            String line = "";

            if (last == words.size() || spaceSlots == 0) {
                for (int i = index; i < last; i++) {
                    line += words.get(i);
                    if (i < last - 1) line += " ";
                }
                while (line.length() < width) line += " ";
            } else {
                int totalSpaces = width - lineLen + spaceSlots;
                int spacePerSlot = totalSpaces / spaceSlots;
                int extra = totalSpaces % spaceSlots;

                for (int i = index; i < last; i++) {
                    line += words.get(i);
                    if (i < last - 1) {
                        for (int s = 0; s < spacePerSlot + (i - index < extra ? 1 : 0); s++) {
                            line += " ";
                        }
                    }
                }
            }

            index = last;
        }
        return System.nanoTime() - start;
    }

    // Method f: Display all outputs
    public static void displayOutput(String originalText, List<String> justified, List<String> centered, long stringTime, long builderTime) {
        System.out.println("\nOriginal Text:");
        System.out.println(originalText);

        System.out.println("\nLeft-Justified Text (with line numbers and character count):");
        for (int i = 0; i < justified.size(); i++) {
            System.out.printf("Line %2d [%3d]: %s\n", i + 1, justified.get(i).length(), justified.get(i));
        }

        System.out.println("\nCenter-Aligned Text:");
        for (String line : centered) {
            System.out.println(line);
        }

        System.out.println("\nPerformance Comparison (nanoseconds):");
        System.out.println("String Concatenation: " + stringTime);
        System.out.println("StringBuilder        : " + builderTime);
        System.out.println("Faster Approach      : " + (stringTime < builderTime ? "String" : "StringBuilder"));
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the text to format:");
        String inputText = scanner.nextLine();

        System.out.print("Enter the desired line width: ");
        int width = scanner.nextInt();
        scanner.close();

        List<String> words = splitWords(inputText);

        long builderStart = System.nanoTime();
        List<String> justified = justifyText(words, width);
        long builderTime = System.nanoTime() - builderStart;

        List<String> centered = centerAlign(justified, width);
        long stringTime = justifyWithStringConcat(words, width);

        displayOutput(inputText, justified, centered, stringTime, builderTime);
    }
}
