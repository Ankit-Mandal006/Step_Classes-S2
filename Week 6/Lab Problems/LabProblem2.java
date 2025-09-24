class Phone {
    protected String brand;
    protected String model;

    public Phone() {
        System.out.println("Phone default constructor");
    }

    public Phone(String brand, String model) {
        this.brand = brand;
        this.model = model;
        System.out.println("Phone parameterized constructor");
    }
}

class SmartPhone extends Phone {
    private String operatingSystem;

    public SmartPhone() {
        super();
        System.out.println("SmartPhone default constructor");
    }

    public SmartPhone(String brand, String model, String operatingSystem) {
        super(brand, model);
        this.operatingSystem = operatingSystem;
        System.out.println("SmartPhone parameterized constructor");
    }

    public void display() {
        System.out.println("Brand: " + brand + ", Model: " + model + ", OS: " + operatingSystem);
    }
}

public class LabProblem2 {
    public static void main(String[] args) {
        System.out.println("--- Creating default SmartPhone ---");
        SmartPhone sp1 = new SmartPhone();

        System.out.println("\n--- Creating parameterized SmartPhone ---");
        SmartPhone sp2 = new SmartPhone("Apple", "iPhone 14", "iOS");

        sp2.display();
    }
}
