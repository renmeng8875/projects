package examples.Chapter6.Item24;

// Broken "immutable" time period class - Page 122

import java.util.*;

public final class Period {
    private final Date start;
    private final Date end;

    /**
     * @param  start the beginning of the period.
     * @param  end the end of the period; must not precede start.
     * @throws IllegalArgumentException if start is after end.
     * @throws NullPointerException if start or end is null.
     *
     * Broken! - Requires defensive copying of parameters.
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(start + " after " + end);
        this.start = start;
        this.end   = end;
    }


/* COMMENTED OUT

    // Repaired constructor - makes defensive copies of parameters - page 123
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end   = new Date(end.getTime());

        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(start +" after "+ end);
    }

 */

    // Broken! - Require defensive copying of internal fields.
    public Date start() {
        return start;
    }
    public Date end() {
        return end;
    }

/* COMMENTED OUT

    // Repaired accessors - make defensive copies of internal fields - Page 124
    public Date start() {
        return (Date) start.clone();
    }

    public Date end() {
        return (Date) end.clone();
    }

 */

    public String toString() {
        return start + " - " + end;
    }

    // ...  // Remainder omitted
}
