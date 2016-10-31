package Introduction1;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("A Bad SyncBank", 1000);  
        //SyncBank bank = new SyncBank("A SyncBank", 1000);
        new ClientThread(bank).start();
        new ClientThread(bank).start();
    }
}
