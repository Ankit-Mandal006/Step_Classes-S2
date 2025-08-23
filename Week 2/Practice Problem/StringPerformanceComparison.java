public class StringPerformanceComparison {
    public static void main(String[] args) {
        int iterations = 10000;
        System.out.println("=== PERFORMANCE COMPARISON ===");

        // Test: String concatenation (inefficient)
        long startTime = System.nanoTime();
        String result1 = concatenateWithString(iterations);
        long endTime = System.nanoTime();
        System.out.println("String time: " + (endTime - startTime) + " ns");

        // Test: StringBuilder (efficient)
        startTime = System.nanoTime();
        String result2 = concatenateWithStringBuilder(iterations);
        endTime = System.nanoTime();
        System.out.println("StringBuilder time: " + (endTime - startTime) + " ns");

        // Test: StringBuffer (thread-safe)
        startTime = System.nanoTime();
        String result3 = concatenateWithStringBuffer(iterations);
        endTime = System.nanoTime();
        System.out.println("StringBuffer time: " + (endTime - startTime) + " ns");

        demonstrateStringBuilderMethods();
        demonstrateThreadSafety();
        compareStringComparisonMethods();
        demonstrateMemoryEfficiency();
    }

    // 🐌 Inefficient String concatenation
    public static String concatenateWithString(int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "Java" + i + " ";
        }
        return result;
    }

    // ⚡ Efficient, not thread-safe
    public static String concatenateWithStringBuilder(int iterations) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("Java").append(i).append(" ");
        }
        return sb.toString();
    }

    // ✅ Thread-safe (synchronized)
    public static String concatenateWithStringBuffer(int iterations) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("Java").append(i).append(" ");
        }
        return sb.toString();
    }

    // 🔧 Demo of StringBuilder capabilities
    public static void demonstrateStringBuilderMethods() {
        System.out.println("\n=== STRINGBUILDER METHODS ===");
        StringBuilder sb = new StringBuilder("Hello World");

        sb.append("!");
        sb.insert(6, "Java ");
        sb.delete(0, 6);
        sb.deleteCharAt(4);
        sb.replace(0, 4, "Hi");
        sb.setCharAt(0, 'H');
        sb.reverse();
        sb.reverse();  // reverse back

        System.out.println("Modified: " + sb);
        System.out.println("Capacity: " + sb.capacity());
        sb.ensureCapacity(100);
        System.out.println("Ensured Capacity (100): " + sb.capacity());
        sb.trimToSize();
        System.out.println("Trimmed Capacity: " + sb.capacity());
    }

    // ⚠️ Thread safety demo
    public static void demonstrateThreadSafety() {
        System.out.println("\n=== THREAD SAFETY DEMO ===");

        StringBuffer buffer = new StringBuffer("Safe ");
        StringBuilder builder = new StringBuilder("Unsafe ");

        Runnable bufferTask = () -> {
            for (int i = 0; i < 1000; i++) {
                buffer.append("A");
            }
        };

        Runnable builderTask = () -> {
            for (int i = 0; i < 1000; i++) {
                builder.append("B");
            }
        };

        Thread t1 = new Thread(bufferTask);
        Thread t2 = new Thread(bufferTask);
        t1.start(); t2.start();

        try { t1.join(); t2.join(); } catch (Exception ignored) {}

        System.out.println("StringBuffer length (thread-safe): " + buffer.length());

        Thread t3 = new Thread(builderTask);
        Thread t4 = new Thread(builderTask);
        t3.start(); t4.start();

        try { t3.join(); t4.join(); } catch (Exception ignored) {}

        System.out.println("StringBuilder length (not thread-safe): " + builder.length());
    }

    // 📏 Compare string comparisons
    public static void compareStringComparisonMethods() {
        System.out.println("\n=== STRING COMPARISON METHODS ===");

        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");

        System.out.println("== comparison: " + (str1 == str2));        // true
        System.out.println("== (with new): " + (str1 == str3));         // false
        System.out.println("equals(): " + str1.equals(str3));           // true
        System.out.println("equalsIgnoreCase(): " + str1.equalsIgnoreCase("hello")); // true
        System.out.println("compareTo(): " + str1.compareTo(str3));     // 0
        System.out.println("compareToIgnoreCase(): " + str1.compareToIgnoreCase("hello")); // 0
    }

    // 🧠 Memory efficiency demo
    public static void demonstrateMemoryEfficiency() {
        System.out.println("\n=== MEMORY EFFICIENCY ===");

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();  // request garbage collection

        long before = runtime.totalMemory() - runtime.freeMemory();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append("Java");
        }

        long after = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory used by StringBuilder (~100K appends): " + (after - before) / 1024 + " KB");

        System.out.println("Final capacity: " + sb.capacity());
        sb.trimToSize();
        System.out.println("Trimmed capacity: " + sb.capacity());
    }
}
