package TwoPhaseTermination.A7;

public class HanoiThread extends Thread {
    // �Ѿ��ͳ���ֹ������Ϊtrue
    private volatile boolean shutdownRequested = false;
    //�ͳ���ֹ�����ʱ��
    private volatile long requestedTimeMillis = 0;

    //  ��ֹ����
    public void shutdownRequest() {
        requestedTimeMillis = System.currentTimeMillis();
        shutdownRequested = true;
        interrupt();
    }

    //  �ж���ֹ�����Ƿ��Ѿ��ͳ��X
    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    // �����@
    public void run() {
        try {
            for (int level = 0; !shutdownRequested; level++) {
                System.out.println("==== Level " + level + " ====");
                doWork(level, 'A', 'B', 'C');
                System.out.println("");
            }
        } catch (InterruptedException e) {
        } finally {
            doShutdown();
        }
    }

    // ��ҵ
    private void doWork(int level, char posA, char posB, char posC) throws InterruptedException {
        if (level > 0) {
            if (shutdownRequested) {
                throw new InterruptedException();
            }
            doWork(level - 1, posA, posC, posB);
            System.out.print(posA + "->" + posB + " ");
            doWork(level - 1, posC, posB, posA);
        }
    }

    // ��ֹ����
    private void doShutdown() {
        long time = System.currentTimeMillis() - requestedTimeMillis;
        System.out.println("doShutdown: Latency = " + time + " msec.");
    }
}
