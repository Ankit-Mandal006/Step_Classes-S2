import java.util.Scanner;

public class StringConcatPerformance {

    // Method 1: String Concatenation using "+"
    public static long testStringConcat(int iterations) {
        long startTime = System.currentTimeMillis();
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "abc"; // Not efficient
        }
        long endTime = System.currentTimeMillis();
        System.out.println("String (+): Final Length = " + result.length());
        return endTime - startTime;
    }

    // Method 2: StringBuilder
    public static long testStringBuilder(int iterations) {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("abc");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("StringBuilder: Final Length = " + sb.length());
        return endTime - startTime;
    }

    // Method 3: StringBuffer
    public static long testStringBuffer(int iterations) {
        long startTime = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("abc");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("StringBuffer: Final Length = " + sb.length());
        return endTime - startTime;
    }

    // Display results in tabular format
    public static void displayResults(long stringTime, long builderTime, long bufferTime) {
        System.out.println("\nPerformance Comparison:");
        System.out.printf("%-20s%-20s\n", "Method Used", "Time Taken (ms)");
        System.out.println("----------------------------------------");
        System.out.printf("%-20s%-20d\n", "String (+)", stringTime);
        System.out.printf("%-20s%-20d\n", "StringBuilder", builderTime);
        System.out.printf("%-20s%-20d\n", "StringBuffer", bufferTime);

        // Determine memory-efficient method (time-based in this example)
        String efficient = (builderTime <= bufferTime && builderTime <= stringTime) ? "StringBuilder"
                          : (bufferTime <= builderTime && bufferTime <= stringTime) ? "StringBuffer"
                          : "String (+)";
        System.out.println("\nMost Efficient (Time-wise): " + efficient);
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of iterations (e.g., 1000, 10000, 100000): ");
        int iterations = scanner.nextInt();
        scanner.close();

        System.out.println("\nRunning performance tests...");

        long stringTime = testStringConcat(iterations);
        long builderTime = testStringBuilder(iterations);
        long bufferTime = testStringBuffer(iterations);

        displayResults(stringTime, builderTime, bufferTime);
    }
}
