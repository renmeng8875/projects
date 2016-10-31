package examples.Chapter6.Item26;

// Working collection classifier - Page 130

import java.util.*;

public class CollectionClassifier2 {
    public static String classify(Collection c) {
        return (c instanceof Set ? "Set" :
                (c instanceof List ? "List" : "Unknown Collection"));
    }

    public static void main(String[] args) {
        Collection[] tests = new Collection[] {
            new HashSet(),          // A Set
            new ArrayList(),        // A List
            new HashMap().values()  // Neither Set nor List
        };

        for (int i = 0; i < tests.length; i++)
            System.out.println(classify(tests[i]));
    }
}
