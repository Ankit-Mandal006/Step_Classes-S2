public class BankAccount {
    private static String bankName;
    private static int totalAccounts = 0;
    private static double interestRate;

    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        totalAccounts++;
    }

    public static void setBankName(String name) {
        bankName = name;
    }

    public static void setInterestRate(double rate) {
        interestRate = rate;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static void displayBankInfo() {
        System.out.println("Bank Name: " + bankName);
        System.out.println("Total Accounts: " + totalAccounts);
        System.out.println("Interest Rate: " + interestRate + "%");
    }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) balance -= amount;
    }

    public double calculateInterest() {
        return balance * (interestRate / 100);
    }

    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: $" + balance);
        System.out.println("Accrued Interest: $" + calculateInterest());
    }

    public static void main(String[] args) {
        BankAccount.setBankName("Global Trust Bank");
        BankAccount.setInterestRate(3.5);

        BankAccount acc1 = new BankAccount("ACC1001", "John Doe", 1500);
        BankAccount acc2 = new BankAccount("ACC1002", "Jane Smith", 2500);

        BankAccount.displayBankInfo();

        System.out.println();

        
        acc1.displayAccountInfo();
        System.out.println();
        acc2.displayAccountInfo();

        System.out.println();

        
        System.out.println("Total accounts via acc1: " + acc1.getTotalAccounts());
        System.out.println("Total accounts via acc2: " + acc2.getTotalAccounts());
        System.out.println("Total accounts via class: " + BankAccount.getTotalAccounts());

        System.out.println();

        
        acc1.deposit(500);
        acc2.withdraw(1000);

        System.out.println("After transactions:");
        acc1.displayAccountInfo();
        System.out.println();
        acc2.displayAccountInfo();

        System.out.println();

        
        acc1.setInterestRate(4.0);
        BankAccount.displayBankInfo();
    }
}
