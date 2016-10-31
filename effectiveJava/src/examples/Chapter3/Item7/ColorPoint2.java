package examples.Chapter3.Item7;

// Adds an aspect without violating the equals contract - Page 31
public class ColorPoint2 {
    private Point point;
    private Color color;

    public ColorPoint2(int x, int y, Color color) {
        point = new Point(x, y);
        this.color = color;
    }

    /**
     * Returns the point-view of this color point.
     */
    public Point asPoint() {
        return point;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint2))
            return false;
        ColorPoint2 cp = (ColorPoint2)o;
        return cp.point.equals(point) && cp.color.equals(color);
    }

    // ...  // Remainder omitted
    // Note that a hashCode method would be required (Item 8)

    public static void main(String[] args) {
        // Symmetry test
        Point p = new Point(1, 2);
        ColorPoint2 cp = new ColorPoint2(1, 2, Color.RED);
        System.out.println(p.equals(cp));
        System.out.println(cp.equals(p));
        System.out.println();

        // Transitivity test
        ColorPoint2 p1 = new ColorPoint2(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint2 p3 = new ColorPoint2(1, 2, Color.BLUE);
        System.out.println(p1.equals(p2));
        System.out.println(p2.equals(p3));
        System.out.println(p1.equals(p3));
    }
}
