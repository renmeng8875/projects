package TwoPhaseTermination.A2;

public class Main {
    public static void main(String[] args) {
        System.out.println("main: BEGIN");
        try {
            // �����߳�
            CountupThread t = new CountupThread();
            t.start();

            // ��΢�ճ���һ��ʱ��
            Thread.sleep(10000);

            // ���߳��ͳ���������
            System.out.println("main: shutdownRequest");
            t.shutdownRequest();

            System.out.println("main: join");

            // �ȴ��߳̽����
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main: END");
    }
}
