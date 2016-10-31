package Balking.A2_1;

import java.io.IOException;
import java.io.FileWriter;
import java.io.Writer;

public class Data {
    private String filename;    //�޸��ǵ����֦W
    private String content;     // ���ϵ�����
    private boolean changed;    //�޸ĺ�����ݻ�û�洢�Ļ���ֵΪtrue

    public Data(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.changed = true;
    }

    // �޸���������
    public synchronized void change(String newContent) {        
        content = newContent;                                   
        changed = true;                                           
    }                                                           

    // ���������޸ģ��ʹ洢��������
    public synchronized void save() throws IOException {      
        if (!changed) {                                           
            System.out.println(Thread.currentThread().getName() + " balks");
            return;                                             
        }                                                       
        doSave();                                             
        changed = false;                                          
    }                                                           

    // ʵ�����ϴ��浽�������õķ���
    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + " calls doSave, content = " + content);
        Writer writer = new FileWriter(filename);
        writer.write(content);
        writer.close();
    }
}
