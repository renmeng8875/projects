package examples.Chapter7.Item33;

// Compares the performance of repeated String concatenation and 
// repeated String buffer append. - Page 155

import java.util.*;

public class Account {
    private final static String LINE = "********************************************************************************";
    private final static int LINE_WIDTH = LINE.length();

    private final int numItems;

    Account(int numItems) {
        this.numItems = numItems;
    }

    public static void main(String args[]) {
        int numRuns = 3;
        int numReps = 1000;
        int numItems = 100;
        Account account = new Account(numItems);

        System.out.println("String");
        for (int i=0; i<numRuns; i++) {
            long start = System.currentTimeMillis();
            for (int k=0; k<numReps; k++)
                account.statement();
            long end = System.currentTimeMillis();
            System.out.println(end - start + " ms");
        }

        System.out.println("StringBuffer");
        for (int i=0; i<numRuns; i++) {
            long start = System.currentTimeMillis();
            for (int k=0; k<10*numReps; k++)
                account.statement2();
            long end = System.currentTimeMillis();
            System.out.println(((end - start)/10) + " ms");
        }

        System.out.println("StringBuffer without preallocation");
        for (int i=0; i<numRuns; i++) {
            long start = System.currentTimeMillis();
            for (int k=0; k<10*numReps; k++)
                account.statement3();
            long end = System.currentTimeMillis();
            System.out.println(((end - start)/10) + " ms");
        }
    }

    // Inappropriate use of string concatenation - Performs horribly!
    public String statement() {
        String s = "";
        for (int i = 0; i < numItems(); i++)
            s += lineForItem(i);  // String concatenation
        return s;
    }

    // Tuned StringBuffer version
    public String statement2() {
        StringBuffer s = new StringBuffer(numItems() * LINE_WIDTH);
        for (int i = 0; i < numItems(); i++)
            s.append(lineForItem(i));
        return s.toString();
    }

    // Detuned StringBuffer version
    public String statement3() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < numItems(); i++)
            s.append(lineForItem(i));
        return s.toString();
    }

    private int numItems()            { return numItems; }

    private String lineForItem(int i) { return LINE; }
}
