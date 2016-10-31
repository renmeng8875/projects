package Balking.A4;

import java.util.LinkedList;

public class RequestQueue {
    private static final long TIMEOUT = 30000;
    private final LinkedList<Request> queue = new LinkedList<Request>();
    public synchronized Request getRequest() {      
        long start = System.currentTimeMillis(); 
        while (queue.size() <= 0) {                 
            long now = System.currentTimeMillis(); 
            long rest = TIMEOUT - (now - start); 
            if (rest <= 0) {
                throw new LivenessException("thrown by " + Thread.currentThread().getName());
            }
            try {                                   
                wait(rest);
            } catch (InterruptedException e) {      
            }                                       
        }                                           
        return queue.removeFirst();
    }
    public synchronized void putRequest(Request request) {  
        queue.addLast(request);
        notifyAll();                                        
    }
}
