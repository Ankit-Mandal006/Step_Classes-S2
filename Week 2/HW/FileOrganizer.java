import java.util.*;

public class FileOrganizer {

    // Structure to hold file info
    static class FileInfo {
        String originalName;
        String baseName;
        String extension;
        boolean validFormat;
        String category;
        String subcategory;
        String suggestedName;

        FileInfo(String original) {
            this.originalName = original;
            this.validFormat = true; // assume valid until proven otherwise
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<FileInfo> files = new ArrayList<>();

        System.out.println("Enter file names (one per line). Type 'done' to finish:");
        while (true) {
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("done")) break;
            if (!line.isEmpty()) files.add(new FileInfo(line));
        }

        extractComponents(files);
        categorizeFiles(files);
        simulateContentAnalysis(files);
        generateNewNames(files);
        displayReport(files);

        sc.close();
    }

    // b. Using lastIndexOf and substring to split filename and extension
    static void extractComponents(List<FileInfo> files) {
        for (FileInfo f : files) {
            int pos = f.originalName.lastIndexOf('.');
            if (pos > 0 && pos < f.originalName.length() - 1) {
                f.baseName = f.originalName.substring(0, pos);
                f.extension = f.originalName.substring(pos + 1).toLowerCase();
                // validate (no illegal characters)
                if (f.baseName.contains("/") || f.baseName.contains("\\") || f.extension.contains(" ")) {
                    f.validFormat = false;
                }
            } else {
                f.validFormat = false;
            }
        }
    }

    // c. Categorize by known extensions
    static void categorizeFiles(List<FileInfo> files) {
        Map<String, String> categories = new HashMap<>();
        categories.put("txt", "Document");
        categories.put("doc", "Document");
        categories.put("docx", "Document");
        categories.put("pdf", "Document");
        categories.put("jpg", "Image");
        categories.put("jpeg", "Image");
        categories.put("png", "Image");
        categories.put("mp4", "Video");
        categories.put("java", "Code");
        categories.put("py", "Code");
        categories.put("csv", "Data");

        for (FileInfo f : files) {
            if (!f.validFormat) {
                f.category = "Invalid Format";
                continue;
            }
            f.category = categories.getOrDefault(f.extension, "Unknown");
        }
    }

    // e. Simulate reading keywords from baseName for subcategory
    static void simulateContentAnalysis(List<FileInfo> files) {
        for (FileInfo f : files) {
            if (!f.validFormat || f.category.equals("Unknown")) {
                f.subcategory = "N/A";
                continue;
            }

            String name = f.baseName.toLowerCase();
            switch (f.category) {
                case "Document":
                    if (name.contains("resume") || name.contains("cv")) f.subcategory = "Resume";
                    else if (name.contains("report")) f.subcategory = "Report";
                    else f.subcategory = "GeneralDoc";
                    break;
                case "Image":
                    if (name.contains("profile")) f.subcategory = "ProfilePic";
                    else if (name.contains("logo")) f.subcategory = "Logo";
                    else f.subcategory = "GenericImage";
                    break;
                case "Code":
                    if (name.contains("test")) f.subcategory = "TestCode";
                    else f.subcategory = "SourceCode";
                    break;
                default:
                    f.subcategory = f.category;
            }
        }
    }

    // d. Build suggested names using StringBuilder and avoid duplicates
    static void generateNewNames(List<FileInfo> files) {
        Map<String, Integer> counter = new HashMap<>();
        String date = java.time.LocalDate.now().toString();

        for (FileInfo f : files) {
            String base = f.category + "_" + date;
            if (!f.subcategory.equals("N/A") && !f.category.equals("Invalid Format") && !f.category.equals("Unknown")) {
                base += "_" + f.subcategory;
            }
            int c = counter.getOrDefault(base, 0) + 1;
            counter.put(base, c);

            String ext = f.validFormat ? "." + f.extension : "";
            f.suggestedName = base + "_" + c + ext;
        }
    }

    // f. Print summary report
    static void displayReport(List<FileInfo> files) {
        System.out.printf("\n%-30s %-15s %-15s %-25s\n", "Original Name", "Category", "Subcategory", "Suggested Name");
        System.out.println("----------------------------------------------------------------------------------------");
        for (FileInfo f : files) {
            String flag = "";
            if (!f.validFormat) flag = "[Invalid]";
            else if (f.category.equals("Unknown")) flag = "[Unknown Type]";
            System.out.printf("%-30s %-15s %-15s %-25s %s\n",
                    f.originalName, f.category, f.subcategory, f.suggestedName, flag);
        }

        System.out.println("\nCategory Counts:");
        Map<String, Long> stats = new LinkedHashMap<>();
        for (FileInfo f : files) {
            stats.put(f.category, stats.getOrDefault(f.category, 0L) + 1);
        }
        stats.forEach((cat, cnt) -> System.out.printf("%-15s : %d\n", cat, cnt));
    }
}
