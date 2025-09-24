class Fruit {
    protected String color;
    protected String taste;

    public Fruit(String color, String taste) {
        this.color = color;
        this.taste = taste;
    }
}

class Apple extends Fruit {
    protected String variety;

    public Apple(String color, String taste, String variety) {
        super(color, taste);
        this.variety = variety;
    }

    public void display() {
        System.out.println("Apple Details:");
        System.out.println("Color: " + color);
        System.out.println("Taste: " + taste);
        System.out.println("Variety: " + variety);
    }
}

public class LabProblem1 {
    public static void main(String[] args) {
        Apple apple = new Apple("Red", "Sweet", "Fuji");
        apple.display();

        System.out.println("Accessing inherited fields directly:");
        System.out.println("Color: " + apple.color);
        System.out.println("Taste: " + apple.taste);
    }
}
