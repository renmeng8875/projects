package examples.Chapter4.Item16;

// List adapter for int array - Page 86

import java.util.*;

public class IntList {
    static List intArrayAsList(final int[] a) {
        if (a == null)
            throw new NullPointerException();

        return new AbstractList() {
            public Object get(int i) {
                return new Integer(a[i]);
            }

            public int size() {
                return a.length;
            }

            public Object set(int i, Object o) {
                int oldVal = a[i];
                a[i] = ((Integer)o).intValue();
                return new Integer(oldVal);
            }
        };
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int a[] = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = i;

        List l = intArrayAsList(a);

        Collections.shuffle(l);
        System.out.println(l);
        Collections.sort(l);
        System.out.println(l);
    }
}
