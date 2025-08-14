import java.util.Scanner;

public class WordLengthFinder {

    // b. Split text into words using charAt(), no split()
    public static String[] splitIntoWords(String text) {
        // Count number of words first (words separated by spaces)
        int wordCount = 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                wordCount++;
            }
        }
        
        String[] words = new String[wordCount];
        int wordIndex = 0;
        String word = "";
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch != ' ') {
                word += ch;  // Build current word char by char
            } else {
                words[wordIndex++] = word;
                word = "";
            }
        }
        // Add the last word
        words[wordIndex] = word;
        
        return words;
    }

    // c. Method to find string length without using length()
    public static int findLength(String s) {
        int count = 0;
        try {
            while(true) {
                s.charAt(count);
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            // reached end of string
        }
        return count;
    }

    // d. Method to create 2D array with words and their lengths as String
    public static String[][] wordsAndLengths(String[] words) {
        String[][] result = new String[words.length][2];
        for (int i = 0; i < words.length; i++) {
            result[i][0] = words[i];
            result[i][1] = String.valueOf(findLength(words[i]));
        }
        return result;
    }

    // e. Method to find shortest and longest word indexes and return their indexes
    public static int[] findShortestAndLongest(String[][] wordData) {
        int shortestIndex = 0;
        int longestIndex = 0;

        int shortestLength = Integer.parseInt(wordData[0][1]);
        int longestLength = Integer.parseInt(wordData[0][1]);

        for (int i = 1; i < wordData.length; i++) {
            int length = Integer.parseInt(wordData[i][1]);
            if (length < shortestLength) {
                shortestLength = length;
                shortestIndex = i;
            }
            if (length > longestLength) {
                longestLength = length;
                longestIndex = i;
            }
        }

        return new int[] {shortestIndex, longestIndex};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the text:");
        String inputText = sc.nextLine();

        // split text into words
        String[] words = splitIntoWords(inputText);
        
        // get word and lengths 2D array
        String[][] wordLengthArray = wordsAndLengths(words);
        
        // get indexes of shortest and longest word
        int[] indexes = findShortestAndLongest(wordLengthArray);

        System.out.println("Shortest word: " + wordLengthArray[indexes[0]][0] 
                           + " (Length: " + wordLengthArray[indexes[0]][1] + ")");
        System.out.println("Longest word: " + wordLengthArray[indexes[1]][0] 
                           + " (Length: " + wordLengthArray[indexes[1]][1] + ")");

        sc.close();
    }
}
