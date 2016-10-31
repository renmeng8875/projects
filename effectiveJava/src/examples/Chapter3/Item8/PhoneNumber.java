package examples.Chapter3.Item8;

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

    // No hashCode method!

/*
    // Page 39
    public int hashCode() {
        int result = 17;
        result = 37*result + areaCode;
        result = 37*result + exchange;
        result = 37*result + extension;
        return result;
    }
*/

/* COMMENTED OUT
    // Lazily initialized, cached hashCode - page 40
    private volatile int hashCode = 0;  // (See Item 48)

    public int hashCode() {
        if (hashCode == 0) {
            int result = 17;
            result = 37*result + areaCode;
            result = 37*result + exchange;
            result = 37*result + extension;
            hashCode = result;
        }
        return hashCode;
    }
*/

    // ... // Remainder omitted

    public static void main(String[] args) {
        Map m = new HashMap();
        m.put(new PhoneNumber(408, 867, 5309), "Jenny");
        System.out.println(m.get(new PhoneNumber(408, 867, 5309)));
    }
}
