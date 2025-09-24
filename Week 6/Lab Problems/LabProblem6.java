class Box {
    public void pack() {
        System.out.println("Packing the box.");
    }

    public void unpack() {
        System.out.println("Unpacking the box.");
    }
}

class GiftBox extends Box {
    @Override
    public void pack() {
        super.pack();  // Call parent pack()
        System.out.println("Adding a decorative ribbon to the gift box.");
    }

    @Override
    public void unpack() {
        super.unpack();  // Call parent unpack()
        System.out.println("Thank you for the wonderful gift!");
    }
}

public class LabProblem6 {
    public static void main(String[] args) {
        GiftBox giftBox = new GiftBox();

        System.out.println("Packing gift box:");
        giftBox.pack();

        System.out.println("\nUnpacking gift box:");
        giftBox.unpack();
    }
}
