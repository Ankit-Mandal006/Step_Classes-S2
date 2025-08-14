import java.util.Scanner;

public class FirstNonRepeatingChar {

    // b. Method to find first non-repeating character using charAt()
    public static char findFirstNonRepeatingChar(String text) {
        int[] freq = new int[256];  // ASCII frequency array initialized to 0

        // i & ii. Loop to find frequency of each character
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            freq[ch]++;
        }

        // iii. Loop again to find first char with frequency 1
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (freq[ch] == 1) {
                return ch;
            }
        }

        // Return a special char if none found
        return '\0';  // Null character to indicate no non-repeating character
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = sc.nextLine();

        char result = findFirstNonRepeatingChar(input);

        if (result == '\0') {
            System.out.println("No non-repeating character found.");
        } else {
            System.out.println("First non-repeating character: " + result);
        }

        sc.close();
    }
}
