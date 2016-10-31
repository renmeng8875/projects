package Introduction1;

public class SyncBank {
    private int money;
    private String name;

    public SyncBank(String name, int money) {
        this.name = name;
        this.money = money;
    }

    
    public synchronized void deposit(int m) {
        money += m;
    }

   
    public synchronized boolean withdraw(int m) {
        if (money >= m) {
            money -= m;
            return true;    
        } else {
            return false;  
        }
    }

    public String getName() {
        return name;
    }
}
