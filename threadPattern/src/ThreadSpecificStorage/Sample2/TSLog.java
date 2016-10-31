package ThreadSpecificStorage.Sample2;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TSLog {
    private PrintWriter writer = null;

    // �� ��ʼ��writer�ֶΦ�
    public TSLog(String filename) {
        try {
            writer = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // �[ ����һ��log
    public void println(String s) {
        writer.println(s);
    }

    //  �ر�log
    public void close() {
        writer.println("==== End of log ====");
        writer.close();
    }
}
