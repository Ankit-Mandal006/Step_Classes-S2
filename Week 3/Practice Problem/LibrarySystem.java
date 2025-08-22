import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;
    private double price;
    private int quantity;

    public Book(String title, String author, String isbn, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void displayBookInfo() {
        System.out.println("Title: " + title + ", Author: " + author + ", ISBN: " + isbn + 
                           ", Price: $" + price + ", Quantity: " + quantity);
    }

    public double getTotalValue() {
        return price * quantity;
    }
}

class Library {
    private String libraryName;
    private Book[] books;
    private int totalBooks;

    public Library(String libraryName, int capacity) {
        this.libraryName = libraryName;
        books = new Book[capacity];
        totalBooks = 0;
    }

    public boolean addBook(Book book) {
        if (totalBooks < books.length) {
            books[totalBooks++] = book;
            return true;
        }
        return false;
    }

    public void searchBooks(String keyword) {
        boolean found = false;
        for (int i = 0; i < totalBooks; i++) {
            Book b = books[i];
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                b.displayBookInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found for: " + keyword);
        }
    }

    public void displayInventory() {
        if (totalBooks == 0) {
            System.out.println("Library is empty.");
            return;
        }
        for (int i = 0; i < totalBooks; i++) {
            books[i].displayBookInfo();
        }
    }

    public double calculateTotalValue() {
        double total = 0;
        for (int i = 0; i < totalBooks; i++) {
            total += books[i].getTotalValue();
        }
        return total;
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library("City Library", 100);

        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Calculate Total Value");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Enter price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();
                    sc.nextLine();

                    Book newBook = new Book(title, author, isbn, price, quantity);
                    if (library.addBook(newBook)) {
                        System.out.println("Book added successfully.");
                    } else {
                        System.out.println("Library is full. Cannot add more books.");
                    }
                    break;

                case 2:
                    System.out.print("Enter title or author to search: ");
                    String keyword = sc.nextLine();
                    library.searchBooks(keyword);
                    break;

                case 3:
                    library.displayInventory();
                    break;

                case 4:
                    double totalValue = library.calculateTotalValue();
                    System.out.printf("Total value of all books: $%.2f%n", totalValue);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
