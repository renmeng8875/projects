package examples.Chapter3.Item7;

public class ColorPoint extends Point {
    private Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

/* COMMENTED OUT

    // Broken - violates symmetry!
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        ColorPoint cp = (ColorPoint)o;
        return super.equals(o) && cp.color == color;
    }

*/

    // Broken - violates transitivity.
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;

        // If o is a normal Point, do a color-blind comparison
        if (!(o instanceof ColorPoint))
            return o.equals(this);

        // o is a ColorPoint; do a full comparison
        ColorPoint cp = (ColorPoint)o;
        return super.equals(o) && cp.color == color;
    }

    // ...  // Remainder omitted
    // Note that a hashCode method would be required (Item 8)

    public static void main(String[] args) {
        // Symmetry test
        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.RED);
        System.out.println(p.equals(cp));
        System.out.println(cp.equals(p));
        System.out.println();

        // Transitivity test
        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
        System.out.println(p1.equals(p2));
        System.out.println(p2.equals(p3));
        System.out.println(p1.equals(p3));
    }
}
