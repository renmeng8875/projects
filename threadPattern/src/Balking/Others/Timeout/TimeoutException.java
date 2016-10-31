package Balking.Others.Timeout;

public class TimeoutException extends InterruptedException {
   
	private static final long serialVersionUID = 6507502430286587745L;

	public TimeoutException(String msg) {
        super(msg);
    }
}
