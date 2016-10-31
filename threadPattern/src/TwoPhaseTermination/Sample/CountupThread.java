package TwoPhaseTermination.Sample;

public class CountupThread extends Thread {
    // ��������ֵ����
    private long counter = 0;

    // �w�Ѿ��ͳ���ֹ������Ϊtrue
    private volatile boolean shutdownRequested = false;

    //  ��ֹ����
    public void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    // �P�ж����������Ƿ��Ѿ��ͳ��X
    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    //�����@
    public final void run() {
        try {
            while (!shutdownRequested) {
                doWork();
            }
        } catch (InterruptedException e) {
        } finally {
            doShutdown();
        }
    }

    //  ��ҵ
    private void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWork: counter = " + counter);
        Thread.sleep(500);
    }

    // ��ֹ����
    private void doShutdown() {
        System.out.println("doShutdown: counter = " + counter);
    }
}
