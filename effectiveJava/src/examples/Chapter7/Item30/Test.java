package examples.Chapter7.Item30;

// Broken random number generator idiom - Page 145

import java.util.*;

public class Test {
    public static void main(String[] args) {
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1000000; i++)
            if (random(n) < n/2)
                low++;

        System.out.println(low);
    }

    static Random rnd = new Random();

    // Common but flawed!
    static int random(int n) {
        return Math.abs(rnd.nextInt()) % n;
    }
}
