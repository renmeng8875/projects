package TwoPhaseTermination.A3;

import java.io.IOException;
import java.io.FileWriter;

public class CountupThread extends Thread {
    // ��������ֵ����
    private long counter = 0;

    // �Ѿ��ͳ���ֹ������Ϊtrue
    private volatile boolean shutdownRequested = false;

    // ��ֹ����
    public void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    // �ж����������Ƿ��Ѿ��ͳ��X
    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    //  �����@
    public void run() {
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
    private void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWork: counter = " + counter);
        Thread.sleep(500);
    }

    // ��ֹ����
    private void doShutdown() {
        System.out.println("doShutdown: counter = " + counter);
        System.out.println("doShutdown: Save BEGIN");
        try {
            FileWriter writer = new FileWriter("counter.txt");
            writer.write("counter = " + counter);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("doShutdown: Save END");
    }
}
