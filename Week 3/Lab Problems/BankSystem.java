public class BankSystem {
    // Private instance variables
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    // Static variables
    private static int totalAccounts = 0;
    private static int accountCounter = 1;

    // Constructor
    public BankSystem(String name, double initialDeposit) {
        if (initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative. Setting balance to 0.");
            initialDeposit = 0;
        }
        this.accountHolderName = name;
        this.balance = initialDeposit;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
        } else {
            balance += amount;
            System.out.println("Deposited $" + amount + " into " + accountNumber);
        }
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds in " + accountNumber);
        } else {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " from " + accountNumber);
        }
    }

    // Check balance
    public double checkBalance() {
        return balance;
    }

    // Display account information
    public void displayAccountInfo() {
        System.out.println("=====================================");
        System.out.println("Account Number   : " + accountNumber);
        System.out.println("Account Holder   : " + accountHolderName);
        System.out.println("Balance          : $" + balance);
        System.out.println("=====================================");
    }

    // Static method to get total accounts
    public static int getTotalAccounts() {
        return totalAccounts;
    }

    // Static method to generate account number
    private static String generateAccountNumber() {
        String num = String.format("ACC%03d", accountCounter);
        accountCounter++;
        return num;
    }

    // Main method
    public static void main(String[] args) {
        // Create an array to hold BankSystem objects
        BankSystem[] accounts = new BankSystem[5];

        // Creating 3 accounts
        accounts[0] = new BankSystem("Alice", 500.0);
        accounts[1] = new BankSystem("Bob", 1000.0);
        accounts[2] = new BankSystem("Charlie", 750.0);

        // Display account info
        for (int i = 0; i < 3; i++) {
            accounts[i].displayAccountInfo();
        }

        // Perform transactions
        accounts[0].deposit(200.0);
        accounts[1].withdraw(300.0);
        accounts[2].withdraw(800.0); // Insufficient funds

        // Display updated balances
        System.out.println("\nUpdated Balances:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Account " + (i + 1) + " (" + accounts[i].accountNumber + ") balance: $" + accounts[i].checkBalance());
        }

        // Static method demo
        System.out.println("\nTotal Accounts Created: " + BankSystem.getTotalAccounts());
    }
}
