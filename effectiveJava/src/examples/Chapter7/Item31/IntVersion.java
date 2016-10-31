package examples.Chapter7.Item31;

// Correct version of the program from page 149 using BigDecimal - Page 150

public class IntVersion {
    public static void main(String[] args) {
        int itemsBought = 0;
        int funds = 100;
        for (int price = 10; funds >= price; price += 10) {
            itemsBought++;
            funds -= price;
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Money left over: "+ funds + " cents");
    }
}
