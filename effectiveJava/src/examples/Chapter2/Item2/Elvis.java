package examples.Chapter2.Item2;

// Singleton with final field - page 10
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
        // ...
    }

    // ...  // Remainder omitted

    public static void main(String[] args) {
        System.out.println(Elvis.INSTANCE);
    }
}
