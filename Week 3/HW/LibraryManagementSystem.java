
public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Set library info
        Book.setLibraryName("City Central Library");
        Book.setFinePerDay(2.0);
        Book.setMaxBooksAllowed(5);  // Set to max for any member type

        // Create some books
        Book[] books = new Book[5];
        books[0] = new Book("B001", "Java Programming", "James Gosling", "ISBN001", "Programming");
        books[1] = new Book("B002", "Data Structures", "Mark Allen Weiss", "ISBN002", "Computer Science");
        books[2] = new Book("B003", "Design Patterns", "Erich Gamma", "ISBN003", "Software Engineering");
        books[3] = new Book("B004", "Operating Systems", "Abraham Silberschatz", "ISBN004", "Computer Science");
        books[4] = new Book("B005", "Clean Code", "Robert C. Martin", "ISBN005", "Programming");

        // Create members of different types
        Member student = new Member("M001", "Alice", "Student", "2023-01-01");
        Member faculty = new Member("M002", "Dr. Bob", "Faculty", "2022-08-15");
        Member general = new Member("M003", "Charlie", "General", "2022-10-20");

        // Issue books
        student.issueBook(books[0], "2025-08-01", "2025-08-10"); // 9 days loan
        faculty.issueBook(books[1], "2025-07-25", "2025-08-20"); // 26 days loan
        general.issueBook(books[2], "2025-07-30", "2025-08-05");

        // Try issuing already issued book
        general.issueBook(books[0], "2025-08-01", "2025-08-10");

        // Return books with overdue
        student.returnBook("B001", "2025-08-15"); // 5 days late
        faculty.returnBook("B002", "2025-08-18"); // before due date
        general.returnBook("B003", "2025-08-10"); // 5 days late

        // Renew book (faculty tries to renew a non-issued book)
        faculty.renewBook("B004", "2025-08-25");

        // Display member info
        student.displayMemberInfo();
        faculty.displayMemberInfo();
        general.displayMemberInfo();

        // Generate Library Report
        Book.generateLibraryReport(books);

        // Show overdue books
        Book.getOverdueBooks(books, "2025-08-15");

        // Show most popular books
        Book.getMostPopularBooks(books);
    }
}

class Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private boolean isIssued;
    private String issueDate;  // format "YYYY-MM-DD"
    private String dueDate;

    private int timesIssued;

    private static int totalBooks = 0;
    private static String libraryName;
    private static double finePerDay;
    private static int maxBooksAllowed;

    public Book(String bookId, String title, String author, String isbn, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.isIssued = false;
        this.issueDate = null;
        this.dueDate = null;
        this.timesIssued = 0;
        totalBooks++;
    }

    public static void setLibraryName(String name) {
        libraryName = name;
    }

    public static void setFinePerDay(double fine) {
        finePerDay = fine;
    }

    public static void setMaxBooksAllowed(int maxBooks) {
        maxBooksAllowed = maxBooks;
    }

    public static int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public String getBookId() {
        return bookId;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issue(String issueDate, String dueDate) {
        this.isIssued = true;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        timesIssued++;
    }

    public void returned() {
        this.isIssued = false;
        this.issueDate = null;
        this.dueDate = null;
    }

    public double calculateFine(String returnDate) {
        int overdueDays = DateUtil.daysBetween(dueDate, returnDate);
        if (overdueDays > 0) {
            return overdueDays * finePerDay;
        }
        return 0;
    }

    public void renew(String newDueDate) {
        this.dueDate = newDueDate;
    }

    public void displayBookInfo() {
        System.out.printf("BookID: %s | Title: %s | Author: %s | Category: %s | Issued: %b | Issue Date: %s | Due Date: %s\n",
                bookId, title, author, category, isIssued, issueDate == null ? "-" : issueDate, dueDate == null ? "-" : dueDate);
    }

    // Static methods for reporting

    public static void generateLibraryReport(Book[] books) {
        System.out.println("\n--- Library Report ---");
        System.out.println("Library Name: " + libraryName);
        System.out.println("Total Books: " + totalBooks);
        System.out.println("Books currently issued: ");
        int issuedCount = 0;
        for (Book b : books) {
            if (b.isIssued()) {
                b.displayBookInfo();
                issuedCount++;
            }
        }
        System.out.println("Total issued books: " + issuedCount);
    }

    public static void getOverdueBooks(Book[] books, String currentDate) {
        System.out.println("\n--- Overdue Books as of " + currentDate + " ---");
        boolean anyOverdue = false;
        for (Book b : books) {
            if (b.isIssued() && DateUtil.daysBetween(b.dueDate, currentDate) > 0) {
                b.displayBookInfo();
                anyOverdue = true;
            }
        }
        if (!anyOverdue) {
            System.out.println("No overdue books.");
        }
    }

    public static void getMostPopularBooks(Book[] books) {
        System.out.println("\n--- Most Popular Books (by times issued) ---");
        int maxIssued = 0;
        for (Book b : books) {
            if (b.timesIssued > maxIssued) {
                maxIssued = b.timesIssued;
            }
        }
        for (Book b : books) {
            if (b.timesIssued == maxIssued) {
                System.out.printf("%s (Issued %d times)\n", b.title, b.timesIssued);
            }
        }
    }
}

class Member {
    private String memberId;
    private String memberName;
    private String memberType; // Student, Faculty, General
    private Book[] booksIssued;
    private int booksCount;
    private double totalFines;
    private String membershipDate;

    public Member(String memberId, String memberName, String memberType, String membershipDate) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberType = memberType;
        this.membershipDate = membershipDate;
        int maxAllowed = getMaxBooksAllowedByType();
        this.booksIssued = new Book[maxAllowed]; // Allocate based on member type
        this.booksCount = 0;
        this.totalFines = 0;
    }

    public boolean canIssueMoreBooks() {
        int limit = getMaxBooksAllowedByType();
        return booksCount < limit;
    }

    private int getMaxBooksAllowedByType() {
        switch (memberType.toLowerCase()) {
            case "student": return 3;
            case "faculty": return 5;
            case "general": return 2;
            default: return 1;
        }
    }

    public void issueBook(Book book, String issueDate, String dueDate) {
        if (!canIssueMoreBooks()) {
            System.out.println(memberName + " (" + memberType + ") has reached the max allowed books.");
            return;
        }
        if (book.isIssued()) {
            System.out.println("Book " + book.getBookId() + " is already issued.");
            return;
        }
        book.issue(issueDate, dueDate);
        booksIssued[booksCount++] = book;
        System.out.println(memberName + " issued book: " + book.getBookId());
    }

    public void returnBook(String bookId, String returnDate) {
        for (int i = 0; i < booksCount; i++) {
            if (booksIssued[i].getBookId().equals(bookId)) {
                double fine = booksIssued[i].calculateFine(returnDate);
                totalFines += fine;
                booksIssued[i].returned();

                // Remove book from issued list
                for (int j = i; j < booksCount - 1; j++) {
                    booksIssued[j] = booksIssued[j + 1];
                }
                booksIssued[booksCount - 1] = null;
                booksCount--;

                System.out.println(memberName + " returned book " + bookId + ". Fine: $" + fine);
                return;
            }
        }
        System.out.println("Book " + bookId + " not found in issued books of " + memberName);
    }

    public void renewBook(String bookId, String newDueDate) {
        for (int i = 0; i < booksCount; i++) {
            if (booksIssued[i].getBookId().equals(bookId)) {
                booksIssued[i].renew(newDueDate);
                System.out.println(memberName + " renewed book " + bookId + " till " + newDueDate);
                return;
            }
        }
        System.out.println("Book " + bookId + " not found in issued books of " + memberName);
    }

    public void displayMemberInfo() {
        System.out.println("\nMember ID: " + memberId);
        System.out.println("Name: " + memberName + " (" + memberType + ")");
        System.out.println("Membership Date: " + membershipDate);
        System.out.println("Books Issued:");
        if (booksCount == 0) {
            System.out.println("  No books issued.");
        } else {
            for (int i = 0; i < booksCount; i++) {
                booksIssued[i].displayBookInfo();
            }
        }
        System.out.println("Total Fines: $" + totalFines);
    }
}

class DateUtil {
    // Returns days between two dates in format "YYYY-MM-DD"
    // If endDate < startDate returns 0
    public static int daysBetween(String startDate, String endDate) {
        if (startDate == null || endDate == null) return 0;

        String[] startParts = startDate.split("-");
        String[] endParts = endDate.split("-");

        int startYear = Integer.parseInt(startParts[0]);
        int startMonth = Integer.parseInt(startParts[1]);
        int startDay = Integer.parseInt(startParts[2]);

        int endYear = Integer.parseInt(endParts[0]);
        int endMonth = Integer.parseInt(endParts[1]);
        int endDay = Integer.parseInt(endParts[2]);

        // Simplified calculation ignoring month lengths and leap years
        int daysStart = startYear * 365 + startMonth * 30 + startDay;
        int daysEnd = endYear * 365 + endMonth * 30 + endDay;

        int diff = daysEnd - daysStart;
        return diff > 0 ? diff : 0;
    }
}
