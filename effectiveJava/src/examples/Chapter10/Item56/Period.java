package examples.Chapter10.Item56;

// Immutable class that uses defensive copying - Page 224

import java.util.*;
import java.io.*;

public final class Period implements Serializable {
    private final Date start;
    private final Date end;

    /**
     * @param  start the beginning of the period.
     * @param  end the end of the period; must not precede start.
     * @throws IllegalArgument if start is after end.
     * @throws NullPointerException if start or end is null.
     */
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end   = new Date(end.getTime());

        if (this.start.compareTo(this.end) > 0)
          throw new IllegalArgumentException(start +" > "+ end);
    }

    public Date start () { return (Date) start.clone(); }

    public Date end ()   { return (Date) end.clone(); }

    public String toString() { return start + " - " + end; }

    // ... // Remainder omitted

/* COMMENTED OUT - This readObject method (Page 226) thwarts the BogusPeriod
 * attack (Page 225), but not the MutablePeriod attack (Page 226).

    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        // Check that our invariants are satisfied
        if (start.compareTo(end) > 0)
            throw new InvalidObjectException(start +" after "+ end);
    }
*/

/* COMMENTED OUT - This readObject method (Page 228) thwarts both the
 * BogusPeriod attack (Page 225) and the MutablePeriod attack (Page 226).
 * To use it, you must remove the final modifier from the start and
 * end fields.

    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        // Defensively copy our mutable components
        start = new Date(start.getTime());
        end   = new Date(end.getTime());

        // Check that our invariants are satisfied
        if (start.compareTo(end) > 0)
            throw new InvalidObjectException(start +" after "+ end);
    }

 */


/* COMMENTED OUT - As an alternative to the defensive readObject method,
 * this defensive readResolve method (Page 231) may be used.  It does
 * not demand that you remove the final modifier from start and end.
 * It thwarts both the BogusPeriod attack (Page 225) and the MutablePeriod
 * attack (Page 226).

    // The defensive readResolve idiom
    private Object readResolve() throws ObjectStreamException {
        return new Period(start, end);
    }

 */
}
