package examples.Chapter2.Item2.serializable;

// Serialzable Singleton - Page 11

import java.io.*;

public class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
        // ...
    }

    // ...  // Remainder omitted

    // readResolve method to preserve singleton property
    private Object readResolve() throws ObjectStreamException {
        /*
         * Return the one true Elvis and let the garbage collector
         * take care of the Elvis impersonator.
         */
        return INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println(Elvis.INSTANCE);
    }
}
