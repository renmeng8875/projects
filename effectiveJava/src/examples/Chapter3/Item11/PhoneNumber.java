package examples.Chapter3.Item11;

// PhoneNumber class on page 36 modified to implement Comparable - Page 56-57

import java.util.*;

public final class PhoneNumber implements Comparable {
    private final short areaCode;
    private final short exchange;
    private final short extension;

    public PhoneNumber(int areaCode, int exchange,
                       int extension) {
        rangeCheck(areaCode,   999, "area code");
        rangeCheck(exchange,   999, "exchange");
        rangeCheck(extension, 9999, "extension");
        this.areaCode  = (short) areaCode;
        this.exchange  = (short) exchange;
        this.extension = (short) extension;
    }

    private static void rangeCheck(int arg, int max,
                                   String name) {
        if (arg < 0 || arg > max)
           throw new IllegalArgumentException(name +": " + arg);
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber)o;
        return pn.extension == extension &&
               pn.exchange  == exchange  &&
               pn.areaCode  == areaCode;
    }

    public int hashCode() {
        int result = 17;
        result = 37*result + areaCode;
        result = 37*result + exchange;
        result = 37*result + extension;
        return result;
    }

/* COMMENTED OUT

    // Page 56
    public int compareTo(Object o) {
        PhoneNumber pn = (PhoneNumber)o;

        // Compare area codes
        if (areaCode < pn.areaCode)
            return -1;
        if (areaCode > pn.areaCode)
            return  1;

        // Area codes are equal, compare exchanges
        if (exchange < pn.exchange)
            return -1;
        if (exchange > pn.exchange)
            return  1;

        // Area codes and exchanges are equal, compare extensions
        if (extension < pn.extension)
            return -1;
        if (extension > pn.extension)
            return  1;

        return 0;  // All fields are equal
    }

*/

    public int compareTo(Object o) {
        PhoneNumber pn = (PhoneNumber)o;

        // Compare area codes
        int areaCodeDiff = areaCode - pn.areaCode;
        if (areaCodeDiff != 0)
            return areaCodeDiff;

        // Area codes are equal, compare exchanges
        int exchangeDiff = exchange - pn.exchange;
        if (exchangeDiff != 0)
            return exchangeDiff;

        // Area codes and exchanges are equal, compare extensions
        return extension - pn.extension;
    }

    /**
     * Returns the string representation of this phone number.
     * The string consists of fourteen characters whose format
     * is "(XXX) YYY-ZZZZ", where XXX is the area code, YYY is
     * the extension, and ZZZZ is the exchange.  (Each of the
     * capital letters represents a single decimal digit.)
     *
     * If any of the three parts of this phone number is too small
     * to fill up its field, the field is padded with leading zeros.
     *  For example, if the value of the exchange is 123, the last
     * four characters of the string representation will be "0123".
     *
     * Note that there is a single space separating the closing
     * parenthesis after the area code from the first digit of the
     * exchange.
     */
    public String toString() {
        return "(" + toPaddedString(areaCode, 3) + ") " +
                toPaddedString(exchange,  3) + "-" +
                toPaddedString(extension, 4);
    }

    /**
     * Translates an int to a string of the specified length,
     * padded with leading zeros.  Assumes i >= 0,
     * 1 <= length <= 10, and Integer.toString(i) <= length.
     */
    private static String toPaddedString(int i, int length) {
        String s = Integer.toString(i);
        return ZEROS[length - s.length()] + s;
    }
    private static String[] ZEROS =
        {"", "0", "00", "000", "0000", "00000", 
         "000000", "0000000", "00000000", "000000000"};

    // ... // Remainder omitted

    public static void main(String args[]) {
        int n = Integer.parseInt(args[0]);

        Random rnd = new Random();
        PhoneNumber[] a = new PhoneNumber[n];
        for (int i=0; i<n; i++)
            a[i] = new PhoneNumber(rnd.nextInt(1000), rnd.nextInt(1000),
                                   rnd.nextInt(10000)); 
        Arrays.sort(a);
        System.out.println(Arrays.asList(a));
    }
}
