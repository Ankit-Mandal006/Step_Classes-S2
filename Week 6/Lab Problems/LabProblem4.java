class Color {
    protected String name;

    public Color(String name) {
        this.name = name;
        System.out.println("Color constructor called");
    }
}

class PrimaryColor extends Color {
    protected int intensity;

    public PrimaryColor(String name, int intensity) {
        super(name);
        this.intensity = intensity;
        System.out.println("PrimaryColor constructor called");
    }
}

class RedColor extends PrimaryColor {
    private String shade;

    public RedColor(String name, int intensity, String shade) {
        super(name, intensity);
        this.shade = shade;
        System.out.println("RedColor constructor called");
    }

    public void display() {
        System.out.println("Color: " + name);
        System.out.println("Intensity: " + intensity);
        System.out.println("Shade: " + shade);
    }
}

public class LabProblem4 {
    public static void main(String[] args) {
        RedColor red = new RedColor("Red", 85, "Crimson");
        red.display();
    }
}
