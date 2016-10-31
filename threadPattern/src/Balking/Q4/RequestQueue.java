package Balking.Q4;

import java.util.LinkedList;

import Balking.A4.Request;

public class RequestQueue {
    private final LinkedList<Request> queue = new LinkedList<Request>();
    public synchronized Request getRequest() {      
        while (queue.size() <= 0) {                 
            try {                                   
                wait();
            } catch (InterruptedException e) {      
            }                                       
        }                                           
        return (Request)queue.removeFirst();
    }
    public synchronized void putRequest(Request request) {  
        queue.addLast(request);
        notifyAll();                                        
    }
}
