public class Vehicle {
    protected String make;
    protected String model;
    protected int year;
    protected double fuelLevel;

    public Vehicle(String make, String model, int year, double fuelLevel) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelLevel = fuelLevel;
    }

    public void startVehicle() {
        System.out.println(make + " " + model + " started.");
    }

    public void stopVehicle() {
        System.out.println(make + " " + model + " stopped.");
    }

    public void refuel(double amount) {
        if (amount > 0) {
            fuelLevel += amount;
            System.out.println(make + " " + model + " refueled by " + amount + " liters.");
        }
    }

    public void displayVehicleInfo() {
        System.out.println(year + " " + make + " " + model + " - Fuel Level: " + fuelLevel + "L");
    }

    public static void main(String[] args) {
        Vehicle[] vehicles = new Vehicle[3];

        vehicles[0] = new Car("Toyota", "Camry", 2020, 50, 4);
        vehicles[1] = new Truck("Ford", "F-150", 2018, 80, 1200);
        vehicles[2] = new Motorcycle("Harley-Davidson", "Street 750", 2019, 15, false);

        for (Vehicle v : vehicles) {
            v.startVehicle();
            v.displayVehicleInfo();
            v.refuel(10);
            v.stopVehicle();
            System.out.println();
        }
    }
}

class Car extends Vehicle {
    private int doors;

    public Car(String make, String model, int year, double fuelLevel, int doors) {
        super(make, model, year, fuelLevel);
        this.doors = doors;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Doors: " + doors);
    }
}

class Truck extends Vehicle {
    private int towingCapacity;

    public Truck(String make, String model, int year, double fuelLevel, int towingCapacity) {
        super(make, model, year, fuelLevel);
        this.towingCapacity = towingCapacity;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Towing Capacity: " + towingCapacity + " lbs");
    }
}

class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public Motorcycle(String make, String model, int year, double fuelLevel, boolean hasSidecar) {
        super(make, model, year, fuelLevel);
        this.hasSidecar = hasSidecar;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Has Sidecar: " + (hasSidecar ? "Yes" : "No"));
    }
}
