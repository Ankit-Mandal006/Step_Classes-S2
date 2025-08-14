import java.util.Scanner;

public class BMICalculator {

    // b. Method to find BMI and Status given height in cm and weight in kg
    public static String[] calculateBMIStatus(double weightKg, double heightCm) {
        // Convert height from cm to meters
        double heightM = heightCm / 100.0;
        // Calculate BMI
        double bmi = weightKg / (heightM * heightM);
        
        // Determine BMI status
        String status;
        if (bmi < 18.5) {
            status = "Underweight";
        } else if (bmi < 25) {
            status = "Normal weight";
        } else if (bmi < 30) {
            status = "Overweight";
        } else {
            status = "Obese";
        }
        
        // Return BMI as string rounded to 2 decimals and status
        return new String[] {String.format("%.2f", bmi), status};
    }

    // c. Method to process all members and create 2D array with height, weight, BMI, status
    public static String[][] processBMI(double[][] heightWeight) {
        int n = heightWeight.length;
        String[][] results = new String[n][4];
        
        for (int i = 0; i < n; i++) {
            double weight = heightWeight[i][0];
            double height = heightWeight[i][1];
            String[] bmiStatus = calculateBMIStatus(weight, height);
            
            results[i][0] = String.format("%.2f", height);  // Height in cm
            results[i][1] = String.format("%.2f", weight);  // Weight in kg
            results[i][2] = bmiStatus[0];                    // BMI
            results[i][3] = bmiStatus[1];                    // Status
        }
        
        return results;
    }

    // d. Method to display the results in tabular format
    public static void displayResults(String[][] data) {
        System.out.printf("%-10s | %-10s | %-10s | %-15s%n", "Height(cm)", "Weight(kg)", "BMI", "Status");
        System.out.println("------------------------------------------------------------");
        for (String[] row : data) {
            System.out.printf("%-10s | %-10s | %-10s | %-15s%n", row[0], row[1], row[2], row[3]);
        }
    }

    // e. main method to take input and call above methods
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double[][] heightWeight = new double[10][2];
        
        System.out.println("Enter weight (kg) and height (cm) for 10 team members:");
        for (int i = 0; i < 10; i++) {
            System.out.printf("Person %d - Weight (kg): ", i + 1);
            heightWeight[i][0] = sc.nextDouble();
            System.out.printf("Person %d - Height (cm): ", i + 1);
            heightWeight[i][1] = sc.nextDouble();
        }
        
        String[][] results = processBMI(heightWeight);
        displayResults(results);
        
        sc.close();
    }
}
