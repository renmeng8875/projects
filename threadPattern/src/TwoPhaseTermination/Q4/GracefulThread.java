package TwoPhaseTermination.Q4;

public class GracefulThread extends Thread {
    // �Ѿ��ͳ���ֹ������Ϊtrue
    private volatile boolean shutdownRequested = false;

    // ��ֹ����
    public final void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    // �ж���ֹ�����Ƿ��Ѿ��ͳ�
    public final boolean isShutdownRequested() {
        return shutdownRequested;
    }

    // �����@
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

    // ��ҵ
    protected void doWork() throws InterruptedException {
    }

    // ��ֹ����
    protected void doShutdown() {
    }
}
