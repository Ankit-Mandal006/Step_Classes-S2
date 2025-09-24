class Instrument {
    protected String name;
    protected String material;

    public Instrument(String name, String material) {
        this.name = name;
        this.material = material;
    }

    public void display() {
        System.out.println("Instrument: " + name + ", Material: " + material);
    }
}

class Piano extends Instrument {
    private int keys;

    public Piano(String name, String material, int keys) {
        super(name, material);
        this.keys = keys;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Keys: " + keys);
    }
}

class Guitar extends Instrument {
    private int strings;

    public Guitar(String name, String material, int strings) {
        super(name, material);
        this.strings = strings;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Strings: " + strings);
    }
}

class Drum extends Instrument {
    private String type;

    public Drum(String name, String material, String type) {
        super(name, material);
        this.type = type;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Type: " + type);
    }
}

public class LabProblem5 {
    public static void main(String[] args) {
        Instrument[] instruments = {
            new Piano("Grand Piano", "Wood", 88),
            new Guitar("Acoustic Guitar", "Wood", 6),
            new Drum("Bass Drum", "Metal", "Percussion")
        };

        for (Instrument instrument : instruments) {
            instrument.display();
            System.out.println();
        }
    }
}
