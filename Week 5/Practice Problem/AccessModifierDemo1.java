public class AccessModifierDemo1 {
    private int privateField;
    String defaultField;
    protected double protectedField;
    public boolean publicField;

    public AccessModifierDemo1(int privateField, String defaultField, double protectedField, boolean publicField) {
        this.privateField = privateField;
        this.defaultField = defaultField;
        this.protectedField = protectedField;
        this.publicField = publicField;
    }

    private void privateMethod() {
        System.out.println("Private method called");
    }

    void defaultMethod() {
        System.out.println("Default method called");
    }

    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    public void publicMethod() {
        System.out.println("Public method called");
    }

    public void testInternalAccess() {
        System.out.println(privateField);
        System.out.println(defaultField);
        System.out.println(protectedField);
        System.out.println(publicField);
        privateMethod();
        defaultMethod();
        protectedMethod();
        publicMethod();
    }
}

class ExtendedDemo extends AccessModifierDemo1 {

    public ExtendedDemo(int privateField, String defaultField, double protectedField, boolean publicField) {
        super(privateField, defaultField, protectedField, publicField);
    }

    public void testInheritedAccess() {
        // System.out.println(privateField); // Not accessible
        // System.out.println(defaultField); // Not accessible (different package if packages used)
        System.out.println(protectedField);
        System.out.println(publicField);

        // privateMethod(); // Not accessible
        // defaultMethod(); // Not accessible
        protectedMethod();
        publicMethod();
    }

    @Override
    protected void protectedMethod() {
        System.out.println("Overridden protected method in ExtendedDemo");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Testing AccessModifierDemo1:");
        AccessModifierDemo1 demo = new AccessModifierDemo1(1, "default", 1.1, true);
        // System.out.println(demo.privateField); // Not accessible
        System.out.println(demo.defaultField);
        System.out.println(demo.protectedField);
        System.out.println(demo.publicField);
        // demo.privateMethod(); // Not accessible
        demo.defaultMethod();
        demo.protectedMethod();
        demo.publicMethod();

        System.out.println("\nTesting testInternalAccess method:");
        demo.testInternalAccess();

        System.out.println("\nTesting ExtendedDemo:");
        ExtendedDemo child = new ExtendedDemo(20, "childDefault", 20.5, false);
        System.out.println(child.protectedField);
        System.out.println(child.publicField);
        child.testInheritedAccess();
        child.protectedMethod();
    }
}
