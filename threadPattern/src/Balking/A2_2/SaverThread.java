package Balking.A2_2;

import java.io.IOException;

public class SaverThread extends Thread {
    private Data data;
    public SaverThread(String name, Data data) {
        super(name);
        this.data = data;
    }
    public void run() {
        try {
            while (true) {
                data.save();            // �洢���Ϯ�
                Thread.sleep(1000);     // ��ϢԼ1���
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
