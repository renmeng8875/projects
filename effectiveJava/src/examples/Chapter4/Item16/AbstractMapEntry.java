package examples.Chapter4.Item16;

// Skeletal Implementation - page 87

import java.util.*;

public abstract class AbstractMapEntry implements Map.Entry {
    // Primitives
    public abstract Object getKey();
    public abstract Object getValue();

    // Entries in modifiable maps must override this method
    public Object setValue(Object value) {
        throw new UnsupportedOperationException();
    }

    // Implements the general contract of Map.Entry.equals
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (! (o instanceof Map.Entry))
            return false;
        Map.Entry arg = (Map.Entry)o;

        return eq(getKey(),   arg.getKey()) &&
               eq(getValue(), arg.getValue());
    }

    private static boolean eq(Object o1, Object o2) {
        return (o1 == null ? o2 == null : o1.equals(o2));
    }

    // Implements the general contract of Map.Entry.hashcode
    public int hashCode() {
        return
            (getKey()   == null ? 0 :   getKey().hashCode()) ^
            (getValue() == null ? 0 : getValue().hashCode());
    }
}
