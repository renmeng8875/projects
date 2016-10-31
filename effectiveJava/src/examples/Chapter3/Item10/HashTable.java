package examples.Chapter3.Item10;

public class HashTable implements Cloneable {
    private Entry[] buckets = new Entry[11];

    private static class Entry {
        Object key;
        Object value;
        Entry  next;

        Entry(Object key, Object value, Entry next) {
            this.key   = key;
            this.value = value;
            this.next  = next;  
        }
    }

    // Broken - results in shared internal state!
    public Object clone() throws CloneNotSupportedException {
        HashTable result = (HashTable) super.clone();
        result.buckets = (Entry[]) buckets.clone();
        return result;
    }

    // ... // Remainder omitted
}
