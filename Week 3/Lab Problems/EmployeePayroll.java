public class EmployeePayroll {
    // Private instance variables
    private String empId;
    private String empName;
    private String department;
    private double baseSalary;
    private String empType;

    private static int totalEmployees = 0;
    private static int empCounter = 1;

    // Additional fields for salary calculation
    private double bonus;         // for full-time
    private double hourlyRate;    // for part-time
    private int hoursWorked;      // for part-time
    private double fixedAmount;   // for contract

    private double salary;
    private double tax;

    // Constructor for Full-Time Employee
    public EmployeePayroll(String empName, String department, double baseSalary, double bonus) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
        this.empType = "Full-Time";
        totalEmployees++;
    }

    // Constructor for Part-Time Employee
    public EmployeePayroll(String empName, String department, double hourlyRate, int hoursWorked) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.empType = "Part-Time";
        totalEmployees++;
    }

    // Constructor for Contract Employee
    public EmployeePayroll(String empName, String department, double fixedAmount) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.fixedAmount = fixedAmount;
        this.empType = "Contract";
        totalEmployees++;
    }

    // Overloaded calculateSalary methods
    public void calculateSalary() {
        if (empType.equals("Full-Time")) {
            salary = baseSalary + bonus;
        } else if (empType.equals("Part-Time")) {
            salary = hourlyRate * hoursWorked;
        } else if (empType.equals("Contract")) {
            salary = fixedAmount;
        }
    }

    // Overloaded calculateTax method
    public void calculateTax() {
        if (empType.equals("Full-Time")) {
            tax = salary * 0.2;  // 20% tax
        } else if (empType.equals("Part-Time")) {
            tax = salary * 0.1;  // 10% tax
        } else if (empType.equals("Contract")) {
            tax = salary * 0.05; // 5% tax
        }
    }

    // Generate pay slip
    public void generatePaySlip() {
        calculateSalary();
        calculateTax();

        System.out.println("========== Pay Slip ==========");
        System.out.println("Employee ID   : " + empId);
        System.out.println("Name          : " + empName);
        System.out.println("Department    : " + department);
        System.out.println("Employee Type : " + empType);
        System.out.println("Gross Salary  : $" + salary);
        System.out.println("Tax Deducted  : $" + tax);
        System.out.println("Net Salary    : $" + (salary - tax));
        System.out.println("==============================\n");
    }

    // Display employee info
    public void displayEmployeeInfo() {
        System.out.println("Employee ID   : " + empId);
        System.out.println("Name          : " + empName);
        System.out.println("Department    : " + department);
        System.out.println("Employee Type : " + empType);
        System.out.println("------------------------------");
    }

    // Static method to get total employees
    public static int getTotalEmployees() {
        return totalEmployees;
    }

    // Static method to generate employee ID
    private static String generateEmpId() {
        return String.format("EMP%03d", empCounter++);
    }

    // Static method to generate payroll report
    public static void generateCompanyPayrollReport(EmployeePayroll[] employees) {
        double totalPayroll = 0;
        System.out.println("===== Company Payroll Report =====");
        for (EmployeePayroll emp : employees) {
            if (emp != null) {
                emp.calculateSalary();
                totalPayroll += emp.salary;
                System.out.println(emp.empName + " - $" + emp.salary);
            }
        }
        System.out.println("Total Employees : " + totalEmployees);
        System.out.println("Total Payroll   : $" + totalPayroll);
        System.out.println("==================================");
    }

    // Main method (no separate Main class as per your request)
    public static void main(String[] args) {
        // Create array of employees
        EmployeePayroll[] employees = new EmployeePayroll[5];

        employees[0] = new EmployeePayroll("Alice", "HR", 5000.0, 500.0); // Full-time
        employees[1] = new EmployeePayroll("Bob", "IT", 20.0, 100);       // Part-time
        employees[2] = new EmployeePayroll("Charlie", "Finance", 4000.0); // Contract

        // Display info
        System.out.println("=== Employee Information ===");
        for (EmployeePayroll emp : employees) {
            if (emp != null) emp.displayEmployeeInfo();
        }

        // Generate pay slips
        System.out.println("\n=== Pay Slips ===");
        for (EmployeePayroll emp : employees) {
            if (emp != null) emp.generatePaySlip();
        }

        // Generate company-wide payroll report
        System.out.println("\n=== Company Payroll Report ===");
        EmployeePayroll.generateCompanyPayrollReport(employees);
    }
}
