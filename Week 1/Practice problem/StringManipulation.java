public class StringManipulation {
    public static void main(String[] args) {
        // 1. Create string using String literal
        String str1 = "Java Programming";

        // 2. Create string using new String() constructor
        String str2 = new String("Java Programming");

        // 3. Create string from character array
        char[] charArray = {'J', 'a', 'v', 'a', ' ', 'P', 'r', 'o', 'g', 'r', 'a', 'm', 'm', 'i', 'n', 'g'};
        String str3 = new String(charArray);

        // Compare strings using '==' (reference equality)
        System.out.println("str1 == str2: " + (str1 == str2));  // false, different objects
        System.out.println("str1 == str3: " + (str1 == str3));  // false, different objects

        // Compare strings using .equals() (content equality)
        System.out.println("str1.equals(str2): " + str1.equals(str2));  // true, same content
        System.out.println("str1.equals(str3): " + str1.equals(str3));  // true, same content

        System.out.println("\nExplanation:");
        System.out.println("'==' checks if both references point to the same object.");
        System.out.println("'.equals()' checks if the values inside the objects are equal.");

        // Create string with escape sequences
        String quote = "Programming Quote:\n\"Code is poetry\" - Unknown\nPath: C:\\Java\\Projects";

        System.out.println("\n" + quote);
    }
}
