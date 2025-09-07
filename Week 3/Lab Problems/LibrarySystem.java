// Class 1: Book
class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    private static int totalBooks = 0;
    private static int availableBooks = 0;
    private static int bookCounter = 1;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.bookId = generateBookId();
        this.isAvailable = true;
        totalBooks++;
        availableBooks++;
    }

    public void issueBook() {
        if (isAvailable) {
            isAvailable = false;
            availableBooks--;
        } else {
            System.out.println("Book already issued: " + title);
        }
    }

    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            availableBooks++;
        } else {
            System.out.println("Book was not issued: " + title);
        }
    }

    public void displayBookInfo() {
        System.out.println("Book ID     : " + bookId);
        System.out.println("Title       : " + title);
        System.out.println("Author      : " + author);
        System.out.println("Available   : " + (isAvailable ? "Yes" : "No"));
        System.out.println("-------------------------------");
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getBookId() {
        return bookId;
    }

    public static int getTotalBooks() {
        return totalBooks;
    }

    public static int getAvailableBooks() {
        return availableBooks;
    }

    private static String generateBookId() {
        return String.format("B%03d", bookCounter++);
    }
}
// Class 2: Member
class Member {
    private String memberId;
    private String memberName;
    private String[] booksIssued;
    private int bookCount;

    private static int memberCounter = 1;

    public Member(String name) {
        this.memberName = name;
        this.memberId = generateMemberId();
        this.booksIssued = new String[5]; // Max 5 books per member
        this.bookCount = 0;
    }

    public void borrowBook(Book book) {
        if (bookCount >= 5) {
            System.out.println(memberName + " has already borrowed 5 books.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is not available: " + book.getBookId());
            return;
        }

        book.issueBook();
        booksIssued[bookCount] = book.getBookId();
        bookCount++;
        System.out.println(memberName + " borrowed book: " + book.getBookId());
    }

    public void returnBook(String bookId, Book[] books) {
        boolean found = false;

        for (int i = 0; i < bookCount; i++) {
            if (booksIssued[i].equals(bookId)) {
                // Remove from booksIssued
                for (int j = i; j < bookCount - 1; j++) {
                    booksIssued[j] = booksIssued[j + 1];
                }
                booksIssued[--bookCount] = null;
                found = true;

                for (Book book : books) {
                    if (book != null && book.getBookId().equals(bookId)) {
                        book.returnBook();
                        System.out.println(memberName + " returned book: " + bookId);
                        return;
                    }
                }
            }
        }

        if (!found) {
            System.out.println("Book ID " + bookId + " not found in " + memberName + "'s issued list.");
        }
    }

    public void displayMemberInfo() {
        System.out.println("Member ID   : " + memberId);
        System.out.println("Name        : " + memberName);
        System.out.print("Books Issued: ");
        for (int i = 0; i < bookCount; i++) {
            System.out.print(booksIssued[i] + " ");
        }
        if (bookCount == 0) System.out.print("None");
        System.out.println("\n-------------------------------");
    }

    private static String generateMemberId() {
        return String.format("M%03d", memberCounter++);
    }
}
// Class 3: LibrarySystem (contains main method)
public class LibrarySystem {
    public static void main(String[] args) {
        // Create book array
        Book[] books = new Book[5];
        books[0] = new Book("Java Programming", "James Gosling");
        books[1] = new Book("Python Basics", "Guido van Rossum");
        books[2] = new Book("Data Structures", "Robert Lafore");

        // Create member array
        Member[] members = new Member[2];
        members[0] = new Member("Alice");
        members[1] = new Member("Bob");

        // Display books
        System.out.println("Books in Library:");
        for (Book book : books) {
            if (book != null) book.displayBookInfo();
        }

        // Members borrow books
        members[0].borrowBook(books[0]);
        members[0].borrowBook(books[1]);
        members[1].borrowBook(books[0]); // Already issued

        // Display members
        System.out.println("\nMembers Info:");
        for (Member member : members) {
            if (member != null) member.displayMemberInfo();
        }

        // Member returns book
        members[0].returnBook("B001", books); // Alice returns Java book

        // Display updated book info
        System.out.println("\nUpdated Book Info:");
        for (Book book : books) {
            if (book != null) book.displayBookInfo();
        }

        // Show statistics
        System.out.println("Total Books: " + Book.getTotalBooks());
        System.out.println("Available Books: " + Book.getAvailableBooks());
    }
}
