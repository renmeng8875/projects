package examples.Chapter7.Item30;

// The program on page 145, using the built in random number generation
// method in place of the broken idiom.

import java.util.*;

public class Test2 {
    public static void main(String[] args) {
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1000000; i++)
            if (rnd.nextInt(n) < n/2)
                low++;

        System.out.println(low);
    }

    static Random rnd = new Random();
}
