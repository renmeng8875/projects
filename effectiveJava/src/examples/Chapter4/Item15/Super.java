package examples.Chapter4.Item15;

// Page 80

public class Super {
    // Broken - constructor invokes overridable method
    public Super() {
        m();
    }

    public void m() {
    }
}
