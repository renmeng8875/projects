package examples.Chapter2.Item2.staticFactory;

// Singleton with static factory
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();

    private Elvis() {
        // ...
    }

    public static Elvis getInstance() {
        return INSTANCE;
    }

    // ...  // Remainder omitted
    public static void main(String[] args) {
        System.out.println(Elvis.INSTANCE);
    }
}
