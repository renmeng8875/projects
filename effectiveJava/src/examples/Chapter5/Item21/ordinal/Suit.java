package examples.Chapter5.Item21.ordinal;

// Ordinal-based typesafe enum - Page 106

import java.util.*;

public class Suit implements Comparable {
    private final String name;

    // Ordinal of next suit to be created
    private static int nextOrdinal = 0;

    // Assign an ordinal to this suit
    private final int ordinal = nextOrdinal++;

    private Suit(String name) { this.name = name; }

    public String toString()  { return name; }

    public int compareTo(Object o) {
        return ordinal - ((Suit)o).ordinal;
    }

    public static final Suit CLUBS    = new Suit("clubs");
    public static final Suit DIAMONDS = new Suit("diamonds");
    public static final Suit HEARTS   = new Suit("hearts");
    public static final Suit SPADES   = new Suit("spades");

    // Exporting constants - Page 106
    private static final Suit[] PRIVATE_VALUES = 
        { CLUBS, DIAMONDS, HEARTS, SPADES };
    public static final List VALUES =
        Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
}
