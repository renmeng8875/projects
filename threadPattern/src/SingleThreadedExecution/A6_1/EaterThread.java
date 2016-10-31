package SingleThreadedExecution.A6_1;

public class EaterThread extends Thread {
    private String name;
    private final Tool lefthand;
    private final Tool righthand;
    public EaterThread(String name, Tool lefthand, Tool righthand) {
        this.name = name;
        this.lefthand = lefthand;
        this.righthand = righthand;
    }
    public void run() {
        while (true) {
            eat();
        }
    }
    public void eat() {
        synchronized (lefthand) {
            System.out.println(name + " takes up " + lefthand + " (left).");
            synchronized (righthand) {
                System.out.println(name + " takes up " + righthand + " (right).");
                System.out.println(name + " is eating now, yam yam!");
                System.out.println(name + " puts down " + righthand + " (right).");
            }
            System.out.println(name + " puts down " + lefthand + " (left).");
        }
        try {
			Thread.sleep(new java.util.Random().nextInt(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
