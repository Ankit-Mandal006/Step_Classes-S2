import java.util.Scanner;

public class StringLengthWithoutLength {

    // Method to find length without using length()
    public static int findLength(String str) {
        int count = 0;
        try {
            while (true) {
                str.charAt(count);
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            // When charAt goes out of bounds, return count
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.next();

        int customLength = findLength(input);
        int builtInLength = input.length();

        System.out.println("Length found using custom method: " + customLength);
        System.out.println("Length found using built-in length(): " + builtInLength);

        scanner.close();
    }
}
