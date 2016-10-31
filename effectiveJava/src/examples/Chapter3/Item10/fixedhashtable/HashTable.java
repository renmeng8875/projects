package examples.Chapter3.Item10.fixedhashtable;

// Page 49
public class HashTable implements Cloneable {
    private Entry[] buckets =  new Entry[11];

    private static class Entry {
        Object key;
        Object value;
        Entry  next;

        Entry(Object key, Object value, Entry next) {
            this.key   = key;
            this.value = value;
            this.next  = next;  
        }

        // Recursively copy the linked list headed by this Entry
        Entry deepCopy() {
            return new Entry(key, value,
                next == null ? null : next.deepCopy());
        }

/* COMMENTED OUT - Iterative alternative to above method - page 49

        // Iteratively copy the linked list headed by this Entry
        Entry deepCopy() {
            Entry result = new Entry(key, value, next);

            for (Entry p = result; p.next != null; p = p.next)
                p.next = new Entry(p.next.key, p.next.value, p.next.next);

            return result;
        }
    } 

*/

    public Object clone() throws CloneNotSupportedException {
        /*HashTable result = (HashTable) super.clone();
        result.buckets = new Entry[buckets.length];
        for (int i = 0; i < buckets.length; i++)
            if (buckets[i] != null)
                result.buckets[i] = (Entry)
                    buckets[i].deepCopy();

        return result;*/
    	return null;
    }

    // ... // Remainder omitted
    }
}
