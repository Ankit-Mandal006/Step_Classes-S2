class Bird {
    public void fly() {
        System.out.println("Some birds can fly");
    }
}

class Penguin extends Bird {
    @Override
    public void fly() {
        System.out.println("Penguins cannot fly");
    }
}

class Eagle extends Bird {
    @Override
    public void fly() {
        System.out.println("Eagles soar high in the sky");
    }
}

public class LabProblem3 {
    public static void main(String[] args) {
        Bird[] birds = {new Bird(), new Penguin(), new Eagle()};

        for (Bird bird : birds) {
            bird.fly();
        }
    }
}
