package Introduction1;


public class Bank {
    private int money;
    private String name;
    
    public Bank(String name, int money) {
        this.name = name;
        this.money = money;
    }
    public void deposit(int m) {
    	try {
			Thread.sleep(new java.util.Random().nextInt(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        money += m;
    }
    public boolean withdraw(int m) {
    	try {
			Thread.sleep(new java.util.Random().nextInt(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if (money >= m) {
            money -= m;
            check();
            return true;
        } else {
            return false;
        }
    }
    public String getName() {
        return name;
    }
    private void check() {
        if (money < 0) {
            System.out.println("余额为负数! money = " + money);
        }
    }
}
