package examples.Chapter2.Item4;

// Creates lots of unnecessary duplicate objects - page 13

import java.util.*;

public class Person {
    private final Date birthDate;
    // Other fields omitted

    public Person(Date birthDate) {
        this.birthDate = birthDate;
    }
    // DON'T DO THIS!
    public boolean isBabyBoomer() {
        Calendar gmtCal = 
            Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
        Date boomStart = gmtCal.getTime();
        gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
        Date boomEnd = gmtCal.getTime();
        return birthDate.compareTo(boomStart) >= 0 &&
               birthDate.compareTo(boomEnd)   <  0;
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
