package examples.Chapter10.Item55;

// StringList with a reasonable custom serialized form - Page 221

import java.io.*;

public class StringList implements Serializable {
    private transient int size   = 0;
    private transient Entry head = null;

    // No longer Serializable!
    private static class Entry {
        String data;
        Entry  next;
        Entry  previous;
    }

    // Appends the specified string to the list
    public void add(String s) {
        Entry e = new Entry();
        e.data = s;
        e.next = head;
        if (head != null)
            head.previous = e;
        head = e;
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        for (Entry e=head; e!=null; e = e.next) {
            b.append(e.data);
            b.append(" ");
        }
        return b.toString();
    }

    /**
     * Serialize this <tt>StringList</tt> instance.
     *
     * @serialData The size of the list (the number of strings
     * it contains) is emitted (<tt>int</tt>), followed by all of
     * its elements (each a <tt>String</tt>), in the proper
     * sequence.
     */
    private void writeObject(ObjectOutputStream s)
            throws IOException {
        s.defaultWriteObject();
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (Entry e = head; e != null; e = e.next)
            s.writeObject(e.data);
    }

    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int size = s.readInt();

        // Read in all elements and insert them in list
        for (int i = 0; i < size; i++)
            add((String)s.readObject());
    }

    // ... // Remainder omitted
}
