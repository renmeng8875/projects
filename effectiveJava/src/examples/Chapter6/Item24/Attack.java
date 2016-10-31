package examples.Chapter6.Item24;

// Attack the internals of a Period instance.

import java.util.*;

public class Attack {
    public static void main(String args[]) {
        // First attack, thwarted by defensive copy in constructor - Page 123
        Date start = new Date();
        Date end = new Date();
        Period p = new Period(start, end);
        System.out.println(p);
        end.setYear(78);  // Modifies internals of p!
        System.out.println(p);
        System.out.println();

        // Second attack, thwarted by defensive copy in accessors - Page 124
        start = new Date();
        end = new Date();
        p = new Period(start, end);
        System.out.println(p);
        p.end().setYear(78);  // Modifies internals of p!
        System.out.println(p);
    }
}
