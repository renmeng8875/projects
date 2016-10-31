package examples.Chapter9.Item49;

// Page 196

import java.util.*;

public abstract class WorkQueue {
    private final List queue = new LinkedList();
    private boolean stopped = false;

    protected WorkQueue() { new WorkerThread().start(); }

    public final void enqueue(Object workItem) {
        synchronized (queue) {
            queue.add(workItem);
            queue.notify();
        }
    }

    public final void stop()  {
        synchronized (queue) {
            stopped = true;
            queue.notify();
        }
    }
    protected abstract void processItem(Object workItem)
        throws InterruptedException;

    // Broken - invokes alien method from synchronized block!
    private class WorkerThread extends Thread {
        public void run() {
            while (true) {  // Main loop
                synchronized (queue) {
                    try {
                        while (queue.isEmpty() && !stopped)
                            queue.wait();
                    } catch (InterruptedException e) {
                        return;
                    }

                    if (stopped)
                        return;

                    Object workItem = queue.remove(0);
                    try {
                        processItem(workItem); // Lock held!
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }

/* COMMENTED OUT

    // Alien method outside synchronized block - "Open call" - Page 198
    private class WorkerThread extends Thread {
        public void run() {
            while (true) {  // Main loop
                Object workItem = null;
                synchronized (queue) {
                    try {
                        while (queue.isEmpty() && !stopped)
                            queue.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                    if (stopped)
                        return;
                    workItem = queue.remove(0);
                }
                try {
                    processItem(workItem); // No lock held
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

*/

}
