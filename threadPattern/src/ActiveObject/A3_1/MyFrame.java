package ActiveObject.A3_1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ActiveObject.A3_1.activeobject.ActiveObject;
import ActiveObject.A3_1.activeobject.ActiveObjectFactory;
import ActiveObject.A3_1.activeobject.Result;

public class MyFrame extends JFrame implements ActionListener {
    
	private static final long serialVersionUID = 8276629356691987135L;
	private final JTextField textfield = new JTextField("word", 10);
    private final JButton button = new JButton("Search");
    private final JTextArea textarea = new JTextArea(20, 30);
    private final ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
    private final static String NEWLINE = System.getProperty("line.separator");

    public MyFrame() {
        super("ActiveObject Sample");
        getContentPane().setLayout(new BorderLayout());

        // North
        JPanel north = new JPanel();
        north.add(new JLabel("Search:"));
        north.add(textfield);
        north.add(button);
        button.addActionListener(this);

        // Center
        JScrollPane center = new JScrollPane(textarea);

        // Layout
        getContentPane().add(north, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    // [Search]��ť������ʱ
    public void actionPerformed(ActionEvent e) {
        searchWord(textfield.getText());
    }

    // ��ʾ
    private void println(String line) {
        textarea.append(line + NEWLINE);
    }

    // ��Ѱ
    private void searchWord(final String word) { 
        // ��Ѱ�ĺ���
        final Result result = activeObject.search(word);
        println("Searching " + word + "...");
        // �ȴ���Ѱ����ִ����
        new Thread() {
            public void run() {
                // �ȴ���
                final String url = (String)result.getResultValue();
                //  ��ý���ˣ���Event Dispatching Thread������ʾ
                SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            MyFrame.this.println("word = " + word + ", URL = " + url);
                        }
                    }
                );
            }
        }.start();
    }
}
