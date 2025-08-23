import java.util.*;

public class CSVAnalyzer {

    static List<String[]> data = new ArrayList<>();
    static int columnCount = 0;
    static final int MAX_COL_WIDTH = 20;

    // a. Input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter CSV-like data (type 'END' to finish):");

        StringBuilder rawInput = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("END")) break;
            rawInput.append(line).append("\n");
        }
        scanner.close();

        parseCSV(rawInput.toString());
        cleanAndValidate();
        performAnalysis();
        formatTable();
        generateSummary();
    }

    // b. Parse CSV without split
    public static void parseCSV(String input) {
        int i = 0;
        List<String> row = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;

        while (i < input.length()) {
            char c = input.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                row.add(field.toString());
                field.setLength(0);
            } else if ((c == '\n' || c == '\r') && !inQuotes) {
                if (field.length() > 0 || row.size() > 0) {
                    row.add(field.toString());
                    field.setLength(0);
                    data.add(row.toArray(new String[0]));
                    columnCount = Math.max(columnCount, row.size());
                    row.clear();
                }
            } else {
                field.append(c);
            }
            i++;
        }

        if (field.length() > 0 || row.size() > 0) {
            row.add(field.toString());
            data.add(row.toArray(new String[0]));
            columnCount = Math.max(columnCount, row.size());
        }
    }

    // c. Clean and validate
    public static void cleanAndValidate() {
        for (int r = 0; r < data.size(); r++) {
            String[] row = data.get(r);
            for (int c = 0; c < row.length; c++) {
                row[c] = row[c].trim().replaceAll("^\"|\"$", ""); // Remove quotes and trim

                // Mark missing or invalid numeric fields
                if (r > 0 && isNumericColumn(c)) {
                    if (!isNumeric(row[c])) {
                        row[c] = "[INVALID]";
                    }
                }
            }
        }
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char ch : str.toCharArray()) {
            if (!Character.isDigit(ch) && ch != '.') return false;
        }
        return true;
    }

    public static boolean isNumericColumn(int colIndex) {
        // Heuristic: if the first data row has a numeric value, assume the column is numeric
        if (data.size() <= 1 || colIndex >= data.get(1).length) return false;
        return isNumeric(data.get(1)[colIndex]);
    }

    // d. Data analysis
    public static void performAnalysis() {
        System.out.println("\n📊 Data Analysis:");

        for (int c = 0; c < columnCount; c++) {
            if (isNumericColumn(c)) {
                double sum = 0;
                int count = 0;
                double min = Double.MAX_VALUE, max = Double.MIN_VALUE;

                for (int r = 1; r < data.size(); r++) {
                    String val = c < data.get(r).length ? data.get(r)[c] : "";
                    if (isNumeric(val)) {
                        double d = Double.parseDouble(val);
                        sum += d;
                        count++;
                        if (d < min) min = d;
                        if (d > max) max = d;
                    }
                }

                double avg = count == 0 ? 0 : sum / count;
                System.out.printf("Column %d [%s]: Min=%.2f Max=%.2f Avg=%.2f\n",
                        c + 1, data.get(0)[c], min, max, avg);
            } else {
                Set<String> unique = new HashSet<>();
                int missing = 0;

                for (int r = 1; r < data.size(); r++) {
                    String val = c < data.get(r).length ? data.get(r)[c] : "";
                    if (val.equals("") || val.equalsIgnoreCase("[INVALID]")) missing++;
                    else unique.add(val);
                }

                System.out.printf("Column %d [%s]: Unique=%d Missing/Invalid=%d\n",
                        c + 1, data.get(0)[c], unique.size(), missing);
            }
        }
    }

    // e. Format output as table
    public static void formatTable() {
        System.out.println("\n📋 Formatted Data Table:\n");

        int[] colWidths = new int[columnCount];
        Arrays.fill(colWidths, 10);

        for (String[] row : data) {
            for (int i = 0; i < columnCount; i++) {
                if (i < row.length) {
                    colWidths[i] = Math.min(MAX_COL_WIDTH,
                            Math.max(colWidths[i], row[i].length()));
                }
            }
        }

        // Print border
        printBorder(colWidths);

        for (int r = 0; r < data.size(); r++) {
            String[] row = data.get(r);
            System.out.print("| ");
            for (int i = 0; i < columnCount; i++) {
                String cell = i < row.length ? row[i] : "";
                System.out.print(padRight(cell, colWidths[i]));
                System.out.print(" | ");
            }
            System.out.println();
            if (r == 0) printBorder(colWidths); // Header separator
        }

        printBorder(colWidths);
    }

    public static void printBorder(int[] widths) {
        for (int w : widths) {
            System.out.print("+-");
            for (int i = 0; i < w; i++) System.out.print("-");
            System.out.print("-+");
        }
        System.out.println();
    }

    public static String padRight(String text, int width) {
        if (text.length() > width) return text.substring(0, width);
        return String.format("%-" + width + "s", text);
    }

    // f. Summary report
    public static void generateSummary() {
        int totalRecords = data.size() - 1;
        int totalCells = totalRecords * columnCount;
        int validCells = 0;

        for (int r = 1; r < data.size(); r++) {
            for (int c = 0; c < columnCount; c++) {
                if (c < data.get(r).length && !data.get(r)[c].isEmpty()
                        && !data.get(r)[c].equalsIgnoreCase("[INVALID]")) {
                    validCells++;
                }
            }
        }

        double completeness = (validCells * 100.0) / totalCells;

        System.out.println("\n📄 Data Summary Report:");
        System.out.println("Total Records (excluding header): " + totalRecords);
        System.out.printf("Data Completeness: %.2f%% (%d/%d valid cells)\n",
                completeness, validCells, totalCells);
    }
}
