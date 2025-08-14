import java.util.Scanner;

public class VowelConsonantCounter {

    // a. Method to check if character is vowel, consonant, or not a letter
    public static String checkChar(char ch) {
        // Convert uppercase to lowercase using ASCII values
        if (ch >= 'A' && ch <= 'Z') {
            ch = (char) (ch + 32);
        }
        
        // Check if character is a letter
        if (ch >= 'a' && ch <= 'z') {
            // Check vowel
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                return "Vowel";
            } else {
                return "Consonant";
            }
        }
        
        return "Not a Letter";
    }

    // b. Method to find vowels and consonants count in a string
    public static int[] countVowelsConsonants(String text) {
        int vowels = 0, consonants = 0;
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            String result = checkChar(ch);
            
            if (result.equals("Vowel")) {
                vowels++;
            } else if (result.equals("Consonant")) {
                consonants++;
            }
        }
        
        return new int[] {vowels, consonants};
    }

    // c. Main function to take input and display results
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter a string:");
        String input = sc.nextLine();
        
        int[] counts = countVowelsConsonants(input);
        
        System.out.println("Number of vowels: " + counts[0]);
        System.out.println("Number of consonants: " + counts[1]);
        
        sc.close();
    }
}
