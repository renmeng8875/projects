package examples.Chapter4.Item17.constantUtilityClass;

public class Test {
   private static final double ELECTRON_MASS = PhysicalConstants.ELECTRON_MASS;

   public static void main(String[] args) {
       System.out.println("Electron mass: " + ELECTRON_MASS + " kg");
   }
}
