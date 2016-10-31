package examples.Chapter4.Item14;

// Broken - Inappropriate use of inheritance! - Page 71

import java.util.*;

public class InstrumentedHashSet extends HashSet {
    // The number of attempted element insertions
    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(Collection c) {
        super(c);
    }


    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    public boolean add(Object o) {
        addCount++;
        return super.add(o);
    }

    public boolean addAll(Collection c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedHashSet s = new InstrumentedHashSet();
        s.addAll(Arrays.asList(new String[] {"Snap","Crackle","Pop"}));
        System.out.println(s.getAddCount());
    }
}
