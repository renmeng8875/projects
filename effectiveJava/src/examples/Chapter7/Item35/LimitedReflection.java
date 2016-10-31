package examples.Chapter7.Item35;

// Reflective instantiation with interface access - Page 159

import java.util.*;

public class LimitedReflection {
    public static void main(String[] args) {
        // Translate the class name into a class object
        Class cl = null;
        try {
            cl = Class.forName(args[0]);
        } catch(ClassNotFoundException e) {
            System.err.println("Class not found.");
            System.exit(1);
        }

        // Instantiate the class
        Set s = null;
        try {
            s = (Set) cl.newInstance();
        } catch(IllegalAccessException e) {
            System.err.println("Class not accessible.");
            System.exit(1);
        } catch(InstantiationException e) {
            System.err.println("Class not instantiable.");
            System.exit(1);
        }

        // Exercise the set
        s.addAll(Arrays.asList(args).subList(1, args.length-1));
        System.out.println(s);
    }
}
