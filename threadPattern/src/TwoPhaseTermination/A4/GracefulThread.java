package TwoPhaseTermination.A4;

public class GracefulThread extends Thread {
    // �w�Ѿ��ͳ���ֹ������Ϊtrue
    private volatile boolean shutdownRequested = false;

    // ��ֹ����
    public final void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    // �P�ж���ֹ�����Ƿ��Ѿ��ͳ��X
    public final boolean isShutdownRequested() {
        return shutdownRequested;
    }

    //  �����@
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

    //��ҵ
    protected void doWork() throws InterruptedException {
    }

    //��ֹ����
    protected void doShutdown() {
    }
}
