// File: AccessModifierDemo.java
package com.company.security;

public class AccessModifierDemo {
    // Fields with different access modifiers
    private int privateField;           // only accessible within this class
    String defaultField;                // accessible within same package
    protected double protectedField;    // accessible in package + subclasses
    public boolean publicField;         // accessible everywhere

    // Constructor that initializes all fields
    public AccessModifierDemo(int privateField, String defaultField, double protectedField, boolean publicField) {
        this.privateField = privateField;
        this.defaultField = defaultField;
        this.protectedField = protectedField;
        this.publicField = publicField;
    }

    // Methods with different access modifiers
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

    // Method to test internal access of all members within this class
    public void testInternalAccess() {
        // Accessing fields
        System.out.println("privateField = " + privateField);
        System.out.println("defaultField = " + defaultField);
        System.out.println("protectedField = " + protectedField);
        System.out.println("publicField = " + publicField);

        // Calling methods
        privateMethod();
        defaultMethod();
        protectedMethod();
        publicMethod();

        // Private members accessible within same class, demonstrated here
    }

    public static void main(String[] args) {
        AccessModifierDemo demo = new AccessModifierDemo(10, "default", 3.14, true);

        // Accessing fields from within the same class (main is static so we use the instance)
        // privateField demo.privateField;  // Error: privateField has private access in AccessModifierDemo
        // However, inside main, which is static, privateField cannot be accessed directly without instance.
        // But we do have instance 'demo' and privateField is private, so it's not accessible here.
        
        // The following lines show what works and what causes errors:

        // System.out.println(demo.privateField); 
        // => Compile-time error: privateField has private access

        System.out.println(demo.defaultField);    // Works - default access same package
        System.out.println(demo.protectedField);  // Works - protected access same package
        System.out.println(demo.publicField);     // Works - public access everywhere

        // Calling methods
        // demo.privateMethod(); 
        // => Compile-time error: privateMethod() has private access

        demo.defaultMethod();     // Works - default access same package
        demo.protectedMethod();   // Works - protected access same package
        demo.publicMethod();      // Works - public access everywhere

        // Call testInternalAccess to show internal accessibility
        demo.testInternalAccess();
    }
}

// Second class in the SAME package
class SamePackageTest {
    public static void testAccess() {
        AccessModifierDemo demo = new AccessModifierDemo(20, "pkg", 2.71, false);

        // Accessing fields
        // System.out.println(demo.privateField);  
        // => Error: privateField has private access in AccessModifierDemo

        System.out.println(demo.defaultField);    // Works - default access same package
        System.out.println(demo.protectedField);  // Works - protected access same package
        System.out.println(demo.publicField);     // Works - public access everywhere

        // Calling methods
        // demo.privateMethod();
        // => Error: privateMethod() has private access in AccessModifierDemo

        demo.defaultMethod();     // Works - default access same package
        demo.protectedMethod();   // Works - protected access same package
        demo.publicMethod();      // Works - public access everywhere
    }
}
