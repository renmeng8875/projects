package examples.Chapter9.Item49;

// Page 198

class DeadlockQueue extends WorkQueue {
    protected void processItem(final Object workItem)
            throws InterruptedException {
        // Create a new thread that returns workItem to queue
        Thread child = new Thread() {
            public void run() { enqueue(workItem); }
        };
        child.start();
        child.join(); // Deadlock!

        // Will never print (unless open call version of WorkerThread is used
        System.out.println(workItem);
    }

    public static void main(String[] args) throws InterruptedException {
        WorkQueue queue = new DeadlockQueue();
        for (int i = 0; i < args.length; i++)
            queue.enqueue(args[i]);

        // Let items print for a while and then stop the queue
        Thread.sleep(1000);
        queue.stop();
    }
}
