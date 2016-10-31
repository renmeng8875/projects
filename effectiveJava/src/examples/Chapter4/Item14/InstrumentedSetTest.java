package examples.Chapter4.Item14;

// Page 74

import java.util.*;

public class InstrumentedSetTest {
    public static void main(String[] args) {
        InstrumentedSet s = new InstrumentedSet(new HashSet());
        s.addAll(Arrays.asList(new String[] {"Snap","Crackle","Pop"}));
        System.out.println(s.getAddCount());

        // Page 75
        List list = new ArrayList();
        Set s1 = new InstrumentedSet(new TreeSet(list));
        int capacity = 7;
        float loadFactor = .66f;
        Set s2 = new InstrumentedSet(new HashSet(capacity, loadFactor));

        // Page 75
        Set s3 = new HashSet();
        f(s3);
    }

    static void f(Set s) {
        InstrumentedSet sInst = new InstrumentedSet(s);
            // ... // Within this method use sInst instead of s
    }
}
