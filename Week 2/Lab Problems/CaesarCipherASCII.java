import java.util.Scanner;

public class CaesarCipherASCII {

    // Encrypt method
    public static String encrypt(String text, int shift) {
        StringBuilder encrypted = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char ch = (char) (((c - 'A' + shift) % 26 + 26) % 26 + 'A');
                encrypted.append(ch);
            } else if (Character.isLowerCase(c)) {
                char ch = (char) (((c - 'a' + shift) % 26 + 26) % 26 + 'a');
                encrypted.append(ch);
            } else {
                encrypted.append(c); // Leave non-letters unchanged
            }
        }

        return encrypted.toString();
    }

    // Decrypt method (reverse of encrypt)
    public static String decrypt(String text, int shift) {
        return encrypt(text, -shift);
    }

    // Display ASCII values of each character in a string
    public static void displayASCII(String label, String text) {
        System.out.println("\n" + label + ":");
        for (char c : text.toCharArray()) {
            System.out.print("'" + c + "'(" + (int) c + ") ");
        }
        System.out.println();
    }

    // Validate if decrypted text matches original
    public static boolean validate(String original, String decrypted) {
        return original.equals(decrypted);
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input
        System.out.print("Enter text to encrypt: ");
        String originalText = scanner.nextLine();

        System.out.print("Enter shift value (e.g., 3): ");
        int shift = scanner.nextInt();

        scanner.close();

        // Process
        String encryptedText = encrypt(originalText, shift);
        String decryptedText = decrypt(encryptedText, shift);

        // Output
        displayASCII("Original Text", originalText);
        displayASCII("Encrypted Text", encryptedText);
        displayASCII("Decrypted Text", decryptedText);

        // Validation
        System.out.println("\nValidation Result: " +
            (validate(originalText, decryptedText) ? "Success " : "Failed "));
    }
}
