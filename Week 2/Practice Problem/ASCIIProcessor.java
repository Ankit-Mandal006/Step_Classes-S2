import java.util.*;

public class ASCIIProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = scanner.nextLine();

        System.out.println("\n=== CHARACTER ANALYSIS ===");
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int ascii = (int) ch;
            String type = classifyCharacter(ch);
            System.out.println("'" + ch + "' -> ASCII: " + ascii + ", Type: " + type);

            if (Character.isLetter(ch)) {
                char toggled = toggleCase(ch);
                System.out.println("   Toggle case: '" + toggled + "' -> ASCII: " + (int) toggled);
                if (Character.isUpperCase(ch)) {
                    System.out.println("   ASCII diff with lowercase: " + (ch - Character.toLowerCase(ch)));
                } else {
                    System.out.println("   ASCII diff with uppercase: " + (Character.toUpperCase(ch) - ch));
                }
            }
        }

        System.out.println("\n=== CAESAR CIPHER ===");
        System.out.print("Enter shift value: ");
        int shift = scanner.nextInt();
        scanner.nextLine();  // consume newline
        String encrypted = caesarCipher(input, shift);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + caesarCipher(encrypted, -shift));

        System.out.println("\n=== ASCII TABLE (65–90) ===");
        displayASCIITable(65, 90);

        System.out.println("\n=== STRING TO ASCII ARRAY ===");
        int[] asciiArray = stringToASCII(input);
        System.out.println("ASCII Array: " + Arrays.toString(asciiArray));

        System.out.println("\n=== ASCII ARRAY TO STRING ===");
        String converted = asciiToString(asciiArray);
        System.out.println("Converted String: " + converted);

        scanner.close();
    }

    public static String classifyCharacter(char ch) {
        if (Character.isUpperCase(ch)) return "Uppercase Letter";
        if (Character.isLowerCase(ch)) return "Lowercase Letter";
        if (Character.isDigit(ch)) return "Digit";
        return "Special Character";
    }

    public static char toggleCase(char ch) {
        if (Character.isUpperCase(ch)) {
            return (char)(ch + 32);
        } else if (Character.isLowerCase(ch)) {
            return (char)(ch - 32);
        } else {
            return ch;
        }
    }

    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                char c = (char)((ch - 'A' + shift + 26) % 26 + 'A');
                result.append(c);
            } else if (Character.isLowerCase(ch)) {
                char c = (char)((ch - 'a' + shift + 26) % 26 + 'a');
                result.append(c);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void displayASCIITable(int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.println(i + " -> '" + (char)i + "'");
        }
    }

    public static int[] stringToASCII(String text) {
        int[] ascii = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            ascii[i] = (int) text.charAt(i);
        }
        return ascii;
    }

    public static String asciiToString(int[] asciiValues) {
        StringBuilder sb = new StringBuilder();
        for (int val : asciiValues) {
            sb.append((char) val);
        }
        return sb.toString();
    }
}
