package examples.Chapter3.Item9;

// Page 36
import java.util.*;

public final class PhoneNumber {
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

    // Page 39
    public int hashCode() {
        int result = 17;
        result = 37*result + areaCode;
        result = 37*result + exchange;
        result = 37*result + extension;
        return result;
    }

    // Page 43
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

    public static void main(String[] args) {
        System.out.println("Failed to connect: " +
                           new PhoneNumber(408, 867, 5309));
    }
}
