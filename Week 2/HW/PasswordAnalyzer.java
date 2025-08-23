import java.util.*;

public class PasswordAnalyzer {

    static class PasswordStats {
        String password;
        int length, uppercase, lowercase, digits, special, score;
        String strength;

        PasswordStats(String password, int length, int upper, int lower, int digits, int special, int score, String strength) {
            this.password = password;
            this.length = length;
            this.uppercase = upper;
            this.lowercase = lower;
            this.digits = digits;
            this.special = special;
            this.score = score;
            this.strength = strength;
        }
    }

    // Method b: Analyze ASCII-based stats
    public static PasswordStats analyzePassword(String password) {
        int upper = 0, lower = 0, digits = 0, special = 0;
        int length = password.length();

        for (int i = 0; i < length; i++) {
            char c = password.charAt(i);
            int ascii = (int) c;

            if (ascii >= 65 && ascii <= 90) upper++;
            else if (ascii >= 97 && ascii <= 122) lower++;
            else if (ascii >= 48 && ascii <= 57) digits++;
            else if (ascii >= 33 && ascii <= 126) special++;
        }

        int score = calculateScore(password, upper, lower, digits, special);
        String strength = getStrength(score);

        return new PasswordStats(password, length, upper, lower, digits, special, score, strength);
    }

    // Method c: Calculate score
    public static int calculateScore(String password, int upper, int lower, int digits, int special) {
        int score = 0;

        if (password.length() > 8) {
            score += (password.length() - 8) * 2;
        }

        if (upper > 0) score += 10;
        if (lower > 0) score += 10;
        if (digits > 0) score += 10;
        if (special > 0) score += 10;

        // Deduct for common patterns
        String pwdLower = password.toLowerCase();
        String[] patterns = { "123", "abc", "password", "qwerty", "111", "000", "admin" };
        for (String pattern : patterns) {
            if (pwdLower.contains(pattern)) {
                score -= 10;
            }
        }

        return Math.max(score, 0); // avoid negative
    }

    public static String getStrength(int score) {
        if (score >= 51) return "Strong";
        else if (score >= 21) return "Medium";
        else return "Weak";
    }

    // Method d: Generate strong password
    public static String generateStrongPassword(int length) {
        if (length < 8) length = 8;

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        // Ensure one from each category
        sb.append(upper.charAt(rand.nextInt(upper.length())));
        sb.append(lower.charAt(rand.nextInt(lower.length())));
        sb.append(digits.charAt(rand.nextInt(digits.length())));
        sb.append(special.charAt(rand.nextInt(special.length())));

        String allChars = upper + lower + digits + special;

        for (int i = 4; i < length; i++) {
            sb.append(allChars.charAt(rand.nextInt(allChars.length())));
        }

        // Shuffle password
        List<Character> charList = new ArrayList<>();
        for (char c : sb.toString().toCharArray()) {
            charList.add(c);
        }
        Collections.shuffle(charList);
        StringBuilder shuffled = new StringBuilder();
        for (char c : charList) {
            shuffled.append(c);
        }

        return shuffled.toString();
    }

    // Method e: Display table
    public static void displayStats(List<PasswordStats> statsList) {
        System.out.printf("\n%-15s %-6s %-8s %-9s %-7s %-13s %-6s %-10s\n",
                "Password", "Len", "Upper", "Lower", "Digits", "SpecialChars", "Score", "Strength");
        System.out.println("-------------------------------------------------------------------------------");
        for (PasswordStats stat : statsList) {
            System.out.printf("%-15s %-6d %-8d %-9d %-7d %-13d %-6d %-10s\n",
                    stat.password, stat.length, stat.uppercase, stat.lowercase,
                    stat.digits, stat.special, stat.score, stat.strength);
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<PasswordStats> statsList = new ArrayList<>();

        System.out.println("Enter passwords to analyze (type 'done' to finish):");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            PasswordStats stats = analyzePassword(input);
            statsList.add(stats);
        }

        displayStats(statsList);

        System.out.print("\nWould you like to generate a strong password? (yes/no): ");
        String choice = scanner.nextLine().trim();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter desired password length: ");
            int length = scanner.nextInt();
            String strongPwd = generateStrongPassword(length);
            System.out.println("Generated Strong Password: " + strongPwd);
        }

        scanner.close();
    }
}
