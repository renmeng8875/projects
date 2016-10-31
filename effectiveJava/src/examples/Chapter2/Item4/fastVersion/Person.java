package examples.Chapter2.Item4.fastVersion;

// Doesn't creates unnecessary duplicate objects - page 14

import java.util.*;

class Person {
    private final Date birthDate;

    public Person(Date birthDate) {
        this.birthDate = birthDate;
    }


    /**
     * The starting and ending dates of the baby boom.
     */
    private static final Date BOOM_START;
    private static final Date BOOM_END;

    static {
        Calendar gmtCal =
            Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
        BOOM_START = gmtCal.getTime();
        gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
        BOOM_END = gmtCal.getTime();
    }

    public boolean isBabyBoomer() {
        return birthDate.compareTo(BOOM_START) >= 0 &&
               birthDate.compareTo(BOOM_END)   <  0;
    }

    public static void main(String[] args) {
        Person p = new Person(new Date());

        long startTime = System.currentTimeMillis();
        for (int i=0; i<1000000; i++)
            p.isBabyBoomer();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println(time+" ms.");
    }
}
