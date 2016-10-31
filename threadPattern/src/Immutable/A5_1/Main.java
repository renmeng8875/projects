package Immutable.A5_1;

public class Main {
    public static void main(String[] args) {
        //����ʵ�� 
        Point p1 = new Point(0, 0);
        Point p2 = new Point(100, 0);
        Line line = new Line(p1, p2);

        //��ʾ
        System.out.println("line = " + line);

        //״̬����
        p1.x = 150;
        p2.x = 150;
        p2.y = 250;

        //�ٶ���ʾ
        System.out.println("line = " + line);
    }
}
