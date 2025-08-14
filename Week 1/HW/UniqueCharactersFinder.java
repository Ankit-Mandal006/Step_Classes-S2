import java.util.Scanner;

public class UniqueCharactersFinder {

    // a. Method to find length of string without using length()
    public static int findLength(String text) {
        int count = 0;
        try {
            while (true) {
                text.charAt(count);
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            // reached end of string
        }
        return count;
    }

    // b. Method to find unique characters using charAt()
    public static char[] findUniqueChars(String text) {
        int length = findLength(text);
        char[] tempUnique = new char[length]; // max size
        int uniqueCount = 0;

        // Loop through each character
        for (int i = 0; i < length; i++) {
            char currentChar = text.charAt(i);
            boolean isUnique = true;

            // Check if currentChar appeared before in tempUnique array
            for (int j = 0; j < uniqueCount; j++) {
                if (tempUnique[j] == currentChar) {
                    isUnique = false;
                    break;
                }
            }

            // If unique, add to tempUnique array
            if (isUnique) {
                tempUnique[uniqueCount] = currentChar;
                uniqueCount++;
            }
        }

        // Create result array of exact size uniqueCount
        char[] uniqueChars = new char[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            uniqueChars[i] = tempUnique[i];
        }

        return uniqueChars;
    }

    // c. main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = sc.nextLine();

        char[] uniqueChars = findUniqueChars(input);

        System.out.print("Unique characters: ");
        for (char ch : uniqueChars) {
            System.out.print(ch + " ");
        }

        sc.close();
    }
}
