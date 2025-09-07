public class PersonalFinanceManager {
    public static void main(String[] args) {
        // Set the bank name (static - shared across all accounts)
        PersonalAccount.setBankName("MyBank Pvt Ltd");

        // Create 3 different personal accounts
        PersonalAccount acc1 = new PersonalAccount("Alice");
        PersonalAccount acc2 = new PersonalAccount("Bob");
        PersonalAccount acc3 = new PersonalAccount("Charlie");

        // Perform income and expense transactions
        acc1.addIncome(5000, "Salary");
        acc1.addExpense(1500, "Groceries");
        acc1.addExpense(500, "Electricity Bill");

        acc2.addIncome(10000, "Freelance Project");
        acc2.addExpense(4000, "Rent");

        acc3.addIncome(7000, "Consulting");
        acc3.addExpense(2000, "Travel");
        acc3.addExpense(1000, "Dining Out");

        // Display account summaries
        acc1.displayAccountSummary();
        acc2.displayAccountSummary();
        acc3.displayAccountSummary();

        // Show total accounts created
        System.out.println("Total Accounts Created: " + PersonalAccount.getTotalAccounts());

        // Show static variable usage (bankName)
        System.out.println("Bank Name (shared across all accounts): " + PersonalAccount.getBankName());
    }
}
class PersonalAccount {
    // Instance variables (private)
    private String accountHolderName;
    private String accountNumber;
    private double currentBalance;
    private double totalIncome;
    private double totalExpenses;

    // Static variables (shared by all objects)
    private static int totalAccounts = 0;
    private static String bankName;
    private static int accountCounter = 1001;

    // Constructor
    public PersonalAccount(String name) {
        this.accountHolderName = name;
        this.accountNumber = generateAccountNumber();
        this.currentBalance = 0.0;
        this.totalIncome = 0.0;
        this.totalExpenses = 0.0;
        totalAccounts++;
    }

    // Instance method to add income
    public void addIncome(double amount, String description) {
        if (amount <= 0) {
            System.out.println("Invalid income amount for " + accountHolderName);
            return;
        }
        totalIncome += amount;
        currentBalance += amount;
        System.out.println(accountHolderName + " received income: " + amount + " (" + description + ")");
    }

    // Instance method to add expense
    public void addExpense(double amount, String description) {
        if (amount <= 0 || amount > currentBalance) {
            System.out.println("Invalid or insufficient balance for expense in account of " + accountHolderName);
            return;
        }
        totalExpenses += amount;
        currentBalance -= amount;
        System.out.println(accountHolderName + " spent: " + amount + " (" + description + ")");
    }

    // Calculate savings
    public double calculateSavings() {
        return totalIncome - totalExpenses;
    }

    // Display account summary
    public void displayAccountSummary() {
        System.out.println("\n=== Account Summary ===");
        System.out.println("Account Holder : " + accountHolderName);
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Bank Name      : " + bankName);
        System.out.println("Total Income   : $" + totalIncome);
        System.out.println("Total Expenses : $" + totalExpenses);
        System.out.println("Current Balance: $" + currentBalance);
        System.out.println("Savings        : $" + calculateSavings());
        System.out.println("--------------------------");
    }

    // Static method to set bank name
    public static void setBankName(String name) {
        bankName = name;
    }

    // Static method to get total accounts
    public static int getTotalAccounts() {
        return totalAccounts;
    }

    // Static method to get bank name
    public static String getBankName() {
        return bankName;
    }

    // Static method to generate unique account number
    public static String generateAccountNumber() {
        return "ACC" + (accountCounter++);
    }
}
