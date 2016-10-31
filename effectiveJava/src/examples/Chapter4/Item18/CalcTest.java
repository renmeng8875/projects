package examples.Chapter4.Item18;

// Test program for use with class on page 94
public class CalcTest {
    public static void main(String args[]) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);

        operate(x, Calculator.Operation.PLUS,   y);
        operate(x, Calculator.Operation.MINUS,  y);
        operate(x, Calculator.Operation.TIMES,  y);
        operate(x, Calculator.Operation.DIVIDE, y);
    }

    static void operate(double x, Calculator.Operation op, double y) {
        Calculator c = new Calculator();
        System.out.println(x + " " + op + " " + y + " = " + 
                           c.calculate(x, op, y));
    }
}
