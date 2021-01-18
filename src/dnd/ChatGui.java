package dnd;

import javax.swing.*;
import java.awt.*;

public class ChatGui extends JFrame implements Runnable {
    private JFrame frame;

    private void chatContent(){
        frame=new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel testpnl =new JPanel();
        JLabel testlbl=new JLabel("dopo connect");

        frame.add(testpnl);
        testpnl.add(testlbl);
        frame.setVisible(true);

    }


    @Override
    public void run() {
        EventQueue.invokeLater(() -> {
            try {
                chatContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
