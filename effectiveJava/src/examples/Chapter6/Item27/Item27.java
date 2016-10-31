package examples.Chapter6.Item27;

import java.util.*;

class Cheese {
    String name;
    Cheese(String name) { this.name = name; }

    public static final Cheese STILTON = new Cheese("Stilton");
}

class CheeseShop {
    private static Cheese[] ECA = new Cheese[0];

    private List cheesesInStock =
    Collections.singletonList(Cheese.STILTON);

    /**
     * @return an array containing all of the cheeses in the shop,
     *         or null if no cheeses are available for purchase.
     */
    public Cheese[] getCheeses() {
        if (cheesesInStock.size() == 0)
            return null;

        return (Cheese[]) cheesesInStock.toArray(ECA);
    }
    private List cheesesInStock2 = null;

    private final static Cheese[] NULL_CHEESE_ARRAY = new Cheese[0];

    /**
     * @return an array containing all of the cheeses in the shop.
     */
    public Cheese[] getCheeses2() {
        return (Cheese[]) cheesesInStock.toArray(NULL_CHEESE_ARRAY);
    }
}

public class Item27 {
    static CheeseShop shop = new CheeseShop();

    public static void main(String[] args) {
        Cheese[] cheeses = shop.getCheeses();
        if (cheeses != null &&
            Arrays.asList(shop.getCheeses()).contains(Cheese.STILTON))
            System.out.println("Jolly good, just the thing.");

        if (Arrays.asList(shop.getCheeses()).contains(Cheese.STILTON))
            System.out.println("Jolly good, just the thing.");
    }
}
