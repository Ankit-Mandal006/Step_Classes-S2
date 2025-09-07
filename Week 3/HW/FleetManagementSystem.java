import java.util.ArrayList;
import java.util.List;

public class FleetManagementSystem {
    public static void main(String[] args) {
        Vehicle.setCompanyName("TransGlobal Logistics");

        // Create vehicles
        Car car1 = new Car("C001", "Toyota", "Camry", 2019, 45000, "Petrol");
        Bus bus1 = new Bus("B001", "Volvo", "7900", 2017, 120000, "Diesel", 50);
        Truck truck1 = new Truck("T001", "Mercedes", "Actros", 2018, 80000, "Diesel", 20000);

        // Assign prices (to update fleetValue)
        car1.setValue(20000);
        bus1.setValue(150000);
        truck1.setValue(120000);

        // Create drivers
        Driver d1 = new Driver("D001", "Alice", "Car");
        Driver d2 = new Driver("D002", "Bob", "Bus");
        Driver d3 = new Driver("D003", "Charlie", "Truck");

        // Assign drivers to vehicles
        car1.assignDriver(d1);
        bus1.assignDriver(d2);
        truck1.assignDriver(d3);

        // Simulate trips and update mileage & fuel
        car1.addTrip(120, 10); // 120 km, 10 liters fuel
        bus1.addTrip(300, 60);
        truck1.addTrip(500, 150);

        // Check maintenance status
        car1.scheduleMaintenance();
        bus1.scheduleMaintenance();
        truck1.scheduleMaintenance();

        // Display fleet summary
        System.out.println("\n--- Fleet Summary ---");
        System.out.println("Total Vehicles: " + Vehicle.getTotalVehicles());
        System.out.println("Total Fleet Value: $" + Vehicle.getFleetValue());
        System.out.println("Total Fuel Consumption: " + Vehicle.getTotalFuelConsumption() + " liters");

        System.out.println("\nVehicles by Type: ");
        List<Vehicle> buses = Vehicle.getVehiclesByType(Bus.class);
        System.out.println("Buses count: " + buses.size());

        double maintenanceCost = Vehicle.calculateTotalMaintenanceCost();
        System.out.println("Total Maintenance Cost (approx): $" + maintenanceCost);

        double utilization = Vehicle.getFleetUtilization();
        System.out.println("Fleet Utilization (trips per vehicle): " + utilization);

        // Display vehicles info
        System.out.println("\nVehicle Details:");
        car1.displayInfo();
        bus1.displayInfo();
        truck1.displayInfo();

        // Display driver info
        System.out.println("\nDriver Details:");
        d1.displayInfo();
        d2.displayInfo();
        d3.displayInfo();
    }
}

// Base Vehicle class
abstract class Vehicle {
    protected String vehicleId;
    protected String brand;
    protected String model;
    protected int year;
    protected double mileage; // in km
    protected String fuelType;
    protected String currentStatus; // Available, Assigned, Maintenance
    protected Driver assignedDriver;

    protected double value; // price of vehicle
    protected double fuelConsumed; // liters for all trips
    protected int totalTrips;

    protected boolean maintenanceDue;

    // Static tracking vars
    private static int totalVehicles = 0;
    private static double fleetValue = 0;
    private static String companyName;
    private static double totalFuelConsumption = 0;

    // Keep a list of all vehicles for utility methods
    private static List<Vehicle> fleet = new ArrayList<>();

    public Vehicle(String vehicleId, String brand, String model, int year, double mileage, String fuelType) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.currentStatus = "Available";
        this.assignedDriver = null;
        this.value = 0;
        this.fuelConsumed = 0;
        this.totalTrips = 0;
        this.maintenanceDue = false;

        totalVehicles++;
        fleet.add(this);
    }

    public static void setCompanyName(String name) {
        companyName = name;
    }

    public static int getTotalVehicles() {
        return totalVehicles;
    }

    public static double getFleetValue() {
        return fleetValue;
    }

    public static double getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public static List<Vehicle> getFleet() {
        return fleet;
    }

    public void setValue(double value) {
        // When set value, add to fleetValue static total
        fleetValue += value;
        this.value = value;
    }

    public void assignDriver(Driver driver) {
        if (driver == null) {
            System.out.println("Cannot assign null driver.");
            return;
        }
        if (!driver.getLicenseType().equalsIgnoreCase(getVehicleType())) {
            System.out.println("Driver license type (" + driver.getLicenseType() +
                    ") does not match vehicle type (" + getVehicleType() + ")");
            return;
        }
        if (assignedDriver != null) {
            System.out.println("Vehicle " + vehicleId + " already has a driver assigned.");
            return;
        }
        assignedDriver = driver;
        driver.assignVehicle(this);
        currentStatus = "Assigned";
        System.out.println("Driver " + driver.driverName + " assigned to vehicle " + vehicleId);
    }

    public void scheduleMaintenance() {
        if (checkServiceDue()) {
            maintenanceDue = true;
            currentStatus = "Maintenance";
            System.out.println("Vehicle " + vehicleId + " requires maintenance.");
        } else {
            maintenanceDue = false;
            System.out.println("Vehicle " + vehicleId + " does not require maintenance.");
        }
    }

    // Abstract method for running cost calculation, implemented differently per vehicle type
    public abstract double calculateRunningCost();

    public void updateMileage(double km) {
        mileage += km;
    }

    public boolean checkServiceDue() {
        // For simplicity, say maintenance every 20,000 km
        return mileage >= 20000 && (mileage % 20000 < 1000);
    }

    public void addTrip(double distanceKm, double fuelUsedLiters) {
        if (!currentStatus.equals("Assigned")) {
            System.out.println("Vehicle " + vehicleId + " is not assigned to any driver; cannot start trip.");
            return;
        }
        updateMileage(distanceKm);
        fuelConsumed += fuelUsedLiters;
        totalFuelConsumption += fuelUsedLiters;
        totalTrips++;
        if (assignedDriver != null) {
            assignedDriver.incrementTrips();
        }
        System.out.println("Trip added for vehicle " + vehicleId + ": distance=" + distanceKm +
                " km, fuel=" + fuelUsedLiters + " L");
    }

    public String getVehicleType() {
        return this.getClass().getSimpleName(); // Car, Bus, Truck
    }

    public void displayInfo() {
        System.out.println("[" + getVehicleType() + "] ID: " + vehicleId + ", Brand: " + brand + ", Model: " + model +
                ", Year: " + year + ", Mileage: " + mileage + " km, FuelType: " + fuelType +
                ", Status: " + currentStatus + ", Trips: " + totalTrips +
                ", Fuel consumed: " + fuelConsumed + " L, Maintenance due: " + maintenanceDue +
                ", Value: $" + value);
        if (assignedDriver != null) {
            System.out.println("  Assigned Driver: " + assignedDriver.driverName);
        } else {
            System.out.println("  No driver assigned.");
        }
    }

    // Static utility methods

    public static double getFleetUtilization() {
        if (fleet.isEmpty()) return 0;
        int totalTripsAll = 0;
        for (Vehicle v : fleet) {
            totalTripsAll += v.totalTrips;
        }
        return (double) totalTripsAll / fleet.size();
    }

    public static double calculateTotalMaintenanceCost() {
        // Assume fixed maintenance cost per vehicle requiring maintenance: $500
        double cost = 0;
        for (Vehicle v : fleet) {
            if (v.checkServiceDue()) {
                cost += 500;
            }
        }
        return cost;
    }

    public static List<Vehicle> getVehiclesByType(Class<? extends Vehicle> cls) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (cls.isInstance(v)) {
                result.add(v);
            }
        }
        return result;
    }
}

// Car class
class Car extends Vehicle {
    public Car(String vehicleId, String brand, String model, int year, double mileage, String fuelType) {
        super(vehicleId, brand, model, year, mileage, fuelType);
    }

    @Override
    public double calculateRunningCost() {
        // Let's say: $0.15 per km + $0.05 per liter fuel cost
        return mileage * 0.15 + fuelConsumed * 0.05;
    }
}

// Bus class
class Bus extends Vehicle {
    private int seatingCapacity;

    public Bus(String vehicleId, String brand, String model, int year, double mileage, String fuelType, int seatingCapacity) {
        super(vehicleId, brand, model, year, mileage, fuelType);
        this.seatingCapacity = seatingCapacity;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    @Override
    public double calculateRunningCost() {
        // Bus: $0.25 per km + $0.07 per liter fuel
        return mileage * 0.25 + fuelConsumed * 0.07;
    }
}

// Truck class
class Truck extends Vehicle {
    private double loadCapacity; // in kg or tons

    public Truck(String vehicleId, String brand, String model, int year, double mileage, String fuelType, double loadCapacity) {
        super(vehicleId, brand, model, year, mileage, fuelType);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    @Override
    public double calculateRunningCost() {
        // Truck: $0.30 per km + $0.08 per liter fuel
        return mileage * 0.30 + fuelConsumed * 0.08;
    }
}

// Driver class
class Driver {
    protected String driverId;
    protected String driverName;
    protected String licenseType; // Car, Bus, Truck
    protected Vehicle assignedVehicle;
    protected int totalTrips;

    public Driver(String driverId, String driverName, String licenseType) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.licenseType = licenseType;
        this.assignedVehicle = null;
        this.totalTrips = 0;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void assignVehicle(Vehicle vehicle) {
        if (assignedVehicle != null) {
            System.out.println("Driver " + driverName + " already has a vehicle assigned.");
            return;
        }
        assignedVehicle = vehicle;
    }

    public void incrementTrips() {
        totalTrips++;
    }

    public void displayInfo() {
        System.out.println("Driver ID: " + driverId + ", Name: " + driverName + ", License: " + licenseType +
                ", Trips: " + totalTrips);
        if (assignedVehicle != null) {
            System.out.println("  Assigned Vehicle: " + assignedVehicle.vehicleId);
        } else {
            System.out.println("  No vehicle assigned.");
        }
    }
}
