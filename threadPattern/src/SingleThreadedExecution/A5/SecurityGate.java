package SingleThreadedExecution.A5;

public class SecurityGate {
    private /*volatile*/  int counter; //加volatile也仍然会有并发问题
    public  void enter() {
        counter++;
    }
    public  void exit() {
        counter--;
    }
    public int getCounter() {
        return counter;
    }
}
