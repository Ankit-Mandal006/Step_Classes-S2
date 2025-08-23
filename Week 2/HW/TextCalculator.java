import java.util.*;

public class TextCalculator {

    // a. Input expressions and process
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter mathematical expressions (type 'done' to stop):");

        while (true) {
            String input = scanner.nextLine().replaceAll("\\s+", "");
            if (input.equalsIgnoreCase("done")) break;

            if (!isValidExpression(input)) {
                System.out.println("❌ Invalid expression. Try again.");
                continue;
            }

            System.out.println("\n✅ Valid Expression: " + input);
            StringBuilder steps = new StringBuilder();
            try {
                int result = evaluateWithParentheses(input, steps);
                displaySteps(input, steps.toString(), result);
            } catch (Exception e) {
                System.out.println("❌ Evaluation error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // b. Expression validator
    public static boolean isValidExpression(String expr) {
        int parenCount = 0;
        char lastChar = ' ';

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (!((c >= '0' && c <= '9') || "+-*/()".indexOf(c) >= 0)) return false;

            if (c == '(') parenCount++;
            if (c == ')') parenCount--;

            if (parenCount < 0) return false;

            if ("+-*/".indexOf(c) >= 0 && (i == 0 || "+-*/".indexOf(lastChar) >= 0)) return false;

            lastChar = c;
        }

        return parenCount == 0;
    }

    // e. Evaluate expressions inside parentheses
    public static int evaluateWithParentheses(String expr, StringBuilder steps) {
        while (expr.contains("(")) {
            int close = expr.indexOf(")");
            int open = expr.lastIndexOf("(", close);

            String inner = expr.substring(open + 1, close);
            int value = evaluate(inner, steps);
            expr = expr.substring(0, open) + value + expr.substring(close + 1);
            steps.append("After evaluating (").append(inner).append("): ").append(expr).append("\n");
        }

        return evaluate(expr, steps);
    }

    // d. Evaluate expression without parentheses
    public static int evaluate(String expr, StringBuilder steps) {
        List<Integer> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        int i = 0;
        while (i < expr.length()) {
            int j = i;
            while (j < expr.length() && Character.isDigit(expr.charAt(j))) j++;
            int num = Integer.parseInt(expr.substring(i, j));
            numbers.add(num);
            i = j;

            if (i < expr.length()) {
                operators.add(expr.charAt(i));
                i++;
            }
        }

        // Handle * and /
        for (int k = 0; k < operators.size(); ) {
            char op = operators.get(k);
            if (op == '*' || op == '/') {
                int a = numbers.get(k);
                int b = numbers.get(k + 1);
                int res = (op == '*') ? a * b : a / b;

                numbers.set(k, res);
                numbers.remove(k + 1);
                operators.remove(k);
                steps.append("Step: ").append(a).append(" ").append(op).append(" ").append(b)
                      .append(" = ").append(res).append("\n");
            } else {
                k++;
            }
        }

        // Handle + and -
        int result = numbers.get(0);
        for (int k = 0; k < operators.size(); k++) {
            char op = operators.get(k);
            int b = numbers.get(k + 1);
            if (op == '+') {
                steps.append("Step: ").append(result).append(" + ").append(b).append(" = ");
                result += b;
                steps.append(result).append("\n");
            } else if (op == '-') {
                steps.append("Step: ").append(result).append(" - ").append(b).append(" = ");
                result -= b;
                steps.append(result).append("\n");
            }
        }

        return result;
    }

    // f. Display all steps
    public static void displaySteps(String expr, String steps, int result) {
        System.out.println("\nCalculation Steps:");
        System.out.println("Original Expression: " + expr);
        System.out.println("---------------------------");
        System.out.println(steps);
        System.out.println("---------------------------");
        System.out.println("Final Result: " + result);
        System.out.println("====================================\n");
    }
}
