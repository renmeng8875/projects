package examples.Chapter3.Item7;

/**
 * Case-insensitive string. Case of the original string is
 * preserved by toString, but ignored in comparisons. - Page 27
 */

public final class CaseInsensitiveString {
    private String s;

    public CaseInsensitiveString(String s) {
        if (s == null)
            throw new NullPointerException();
        this.s = s;
    }

    // Broken - violates symmetry!
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString)
            return s.equalsIgnoreCase(
                ((CaseInsensitiveString)o).s);
        if (o instanceof String)  // One-way interoperability!
            return s.equalsIgnoreCase((String)o);
        return false;
    }

/*  COMMENTED OUT!!

    // Fixed
    public boolean equals(Object o) {
        return o instanceof CaseInsensitiveString &&
            ((CaseInsensitiveString)o).s.equalsIgnoreCase(s);
    }

*/

    // ...  // Remainder omitted
    // Note that a hashCode method would be required (Item 8)

    static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";

        System.out.println(cis.equals(s));
        System.out.println(s.equals(cis));
    }
}
