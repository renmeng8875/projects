package examples.Chapter5.Item20;

// Test program for class hierarchy on page 101
public class ShapeTest {
    public static void main(String args[]) {
        Shape rectangle = new Rectangle(2.0, 3.0);
        Shape circle    = new Circle(2.0);
        Shape square    = new Square(15.0);

        System.out.println("Rectangle area: " + rectangle.area());
        System.out.println("Circle area: "    + circle.area());
        System.out.println("Square area: "    + square.area());
    }
}
