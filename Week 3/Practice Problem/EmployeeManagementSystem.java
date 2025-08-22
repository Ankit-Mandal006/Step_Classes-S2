import java.util.Scanner;

class Employee {
    private static String companyName;
    private static int totalEmployees = 0;

    private String empId;
    private String name;
    private String department;
    private double salary;

    public Employee() {
        this.empId = "";
        this.name = "";
        this.department = "";
        this.salary = 0.0;
        totalEmployees++;
    }

    public Employee(String empId, String name, String department, double salary) {
        this.empId = empId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        totalEmployees++;
    }

    public static void setCompanyName(String name) {
        companyName = name;
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static int getTotalEmployees() {
        return totalEmployees;
    }

    public double calculateAnnualSalary() {
        return salary * 12;
    }

    public void updateSalary(double newSalary) {
        if (newSalary > 0) salary = newSalary;
    }

    public String getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public void displayEmployee() {
        System.out.printf("ID: %s | Name: %s | Dept: %s | Salary: %.2f | Annual: %.2f%n", 
                empId, name, department, salary, calculateAnnualSalary());
    }
}

class Department {
    private String deptName;
    private Employee[] employees;
    private int employeeCount;

    public Department(String deptName, int capacity) {
        this.deptName = deptName;
        this.employees = new Employee[capacity];
        this.employeeCount = 0;
    }

    public boolean addEmployee(Employee emp) {
        if (employeeCount < employees.length) {
            employees[employeeCount++] = emp;
            return true;
        }
        return false;
    }

    public Employee findHighestPaid() {
        if (employeeCount == 0) return null;
        Employee highest = employees[0];
        for (int i = 1; i < employeeCount; i++) {
            if (employees[i].getSalary() > highest.getSalary()) {
                highest = employees[i];
            }
        }
        return highest;
    }

    public double calculateTotalPayroll() {
        double total = 0;
        for (int i = 0; i < employeeCount; i++) {
            total += employees[i].getSalary();
        }
        return total;
    }

    public void displayEmployees() {
        System.out.println("Department: " + deptName);
        for (int i = 0; i < employeeCount; i++) {
            employees[i].displayEmployee();
        }
    }

    public String getDeptName() {
        return deptName;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public Employee[] getEmployees() {
        return employees;
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter company name: ");
        Employee.setCompanyName(scanner.nextLine());

        Department[] departments = new Department[5];
        int deptCount = 0;

        while (true) {
            System.out.println("\n=== EMPLOYEE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Department");
            System.out.println("2. Add Employee");
            System.out.println("3. Display All Employees");
            System.out.println("4. Search Employee");
            System.out.println("5. Department Statistics");
            System.out.println("6. Company Statistics");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (deptCount >= departments.length) {
                        System.out.println("Max departments reached.");
                        break;
                    }
                    System.out.print("Enter department name: ");
                    String deptName = scanner.nextLine();
                    departments[deptCount++] = new Department(deptName, 50);
                    System.out.println("Department added.");
                    break;

                case 2:
                    if (deptCount == 0) {
                        System.out.println("Add a department first.");
                        break;
                    }
                    System.out.print("Enter employee ID: ");
                    String empId = scanner.nextLine();
                    System.out.print("Enter employee name: ");
                    String name = scanner.nextLine();
                    System.out.println("Select Department:");
                    for (int i = 0; i < deptCount; i++) {
                        System.out.printf("%d. %s%n", i + 1, departments[i].getDeptName());
                    }
                    int dChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (dChoice < 1 || dChoice > deptCount) {
                        System.out.println("Invalid department.");
                        break;
                    }

                    System.out.print("Enter salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();

                    Employee emp = new Employee(empId, name, departments[dChoice - 1].getDeptName(), salary);
                    if (departments[dChoice - 1].addEmployee(emp)) {
                        System.out.println("Employee added.");
                    } else {
                        System.out.println("Department full.");
                    }
                    break;

                case 3:
                    for (int i = 0; i < deptCount; i++) {
                        departments[i].displayEmployees();
                        System.out.println();
                    }
                    break;

                case 4:
                    System.out.print("Enter employee name to search: ");
                    String searchName = scanner.nextLine().toLowerCase();
                    boolean found = false;
                    for (int i = 0; i < deptCount; i++) {
                        Employee[] emps = departments[i].getEmployees();
                        for (int j = 0; j < departments[i].getEmployeeCount(); j++) {
                            if (emps[j].getName().toLowerCase().contains(searchName)) {
                                emps[j].displayEmployee();
                                found = true;
                            }
                        }
                    }
                    if (!found) System.out.println("No employee found.");
                    break;

                case 5:
                    for (int i = 0; i < deptCount; i++) {
                        Department dept = departments[i];
                        System.out.println("Department: " + dept.getDeptName());
                        System.out.println("Total Employees: " + dept.getEmployeeCount());
                        Employee highest = dept.findHighestPaid();
                        if (highest != null) {
                            System.out.print("Highest Paid Employee: ");
                            highest.displayEmployee();
                        }
                        System.out.printf("Total Payroll: %.2f%n%n", dept.calculateTotalPayroll());
                    }
                    break;

                case 6:
                    int totalEmps = Employee.getTotalEmployees();
                    double totalPayroll = 0;
                    Employee highestPaid = null;
                    for (int i = 0; i < deptCount; i++) {
                        totalPayroll += departments[i].calculateTotalPayroll();
                        Employee deptHighest = departments[i].findHighestPaid();
                        if (deptHighest != null) {
                            if (highestPaid == null || deptHighest.getSalary() > highestPaid.getSalary()) {
                                highestPaid = deptHighest;
                            }
                        }
                    }
                    System.out.println("Company: " + Employee.getCompanyName());
                    System.out.println("Total Employees: " + totalEmps);
                    System.out.printf("Total Payroll: %.2f%n", totalPayroll);
                    if (highestPaid != null) {
                        System.out.print("Highest Paid Employee: ");
                        highestPaid.displayEmployee();
                    }
                    break;

                case 7:
                    System.out.println("Exiting system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
