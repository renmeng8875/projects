package examples.Chapter9.Item49;

// Page 198

class DisplayQueue extends WorkQueue {
    protected void processItem(Object workItem)
            throws InterruptedException {
        System.out.println(workItem);
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        WorkQueue queue = new DisplayQueue();
        for (int i = 0; i < args.length; i++)
            queue.enqueue(args[i]);

        // Wait for items to print and stop queue
        Thread.sleep((args.length+1) * 1000);
        queue.stop();
    }
}
