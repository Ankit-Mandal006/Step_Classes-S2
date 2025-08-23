import java.util.*;

public class EmailAnalyzer {

    // Email data structure
    static class EmailInfo {
        String email;
        String username;
        String domain;
        String domainName;
        String extension;
        boolean isValid;

        public EmailInfo(String email, boolean isValid, String username, String domain,
                         String domainName, String extension) {
            this.email = email;
            this.username = username;
            this.domain = domain;
            this.domainName = domainName;
            this.extension = extension;
            this.isValid = isValid;
        }
    }

    // Validate email format
    public static boolean isValidEmail(String email) {
        int atIndex = email.indexOf('@');
        int lastAtIndex = email.lastIndexOf('@');
        if (atIndex <= 0 || atIndex != lastAtIndex) return false;

        int dotIndex = email.indexOf('.', atIndex);
        if (dotIndex == -1) return false;

        if (atIndex == 0 || atIndex == email.length() - 1) return false;

        return true;
    }

    // Extract email components
    public static EmailInfo extractComponents(String email) {
        if (!isValidEmail(email)) {
            return new EmailInfo(email, false, "-", "-", "-", "-");
        }

        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        int dotIndex = domain.lastIndexOf('.');
        String domainName = (dotIndex != -1) ? domain.substring(0, dotIndex) : "-";
        String extension = (dotIndex != -1) ? domain.substring(dotIndex + 1) : "-";

        return new EmailInfo(email, true, username, domain, domainName, extension);
    }

    // Analyze email list
    public static void analyzeEmails(List<EmailInfo> emailList) {
        int validCount = 0, invalidCount = 0, usernameTotalLength = 0;
        Map<String, Integer> domainCountMap = new HashMap<>();

        for (EmailInfo info : emailList) {
            if (info.isValid) {
                validCount++;
                usernameTotalLength += info.username.length();
                domainCountMap.put(info.domain, domainCountMap.getOrDefault(info.domain, 0) + 1);
            } else {
                invalidCount++;
            }
        }

        // Find most common domain
        String commonDomain = "-";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : domainCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                commonDomain = entry.getKey();
            }
        }

        double avgUsernameLength = validCount == 0 ? 0.0 : (double) usernameTotalLength / validCount;

        // Print table
        System.out.printf("\n%-30s %-15s %-20s %-15s %-10s %-10s\n",
                "Email", "Username", "Domain", "Domain Name", "Extension", "Valid?");
        System.out.println("-------------------------------------------------------------------------------------------");
        for (EmailInfo info : emailList) {
            System.out.printf("%-30s %-15s %-20s %-15s %-10s %-10s\n",
                    info.email, info.username, info.domain, info.domainName, info.extension,
                    info.isValid ? "Yes" : "No");
        }

        // Print summary
        System.out.println("\nSummary:");
        System.out.println("Total Emails     : " + emailList.size());
        System.out.println("Valid Emails     : " + validCount);
        System.out.println("Invalid Emails   : " + invalidCount);
        System.out.println("Most Common Domain: " + commonDomain);
        System.out.printf("Average Username Length: %.2f\n", avgUsernameLength);
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<EmailInfo> emailList = new ArrayList<>();

        System.out.println("Enter email addresses (type 'done' to finish):");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            EmailInfo info = extractComponents(input);
            emailList.add(info);
        }

        scanner.close();
        analyzeEmails(emailList);
    }
}
