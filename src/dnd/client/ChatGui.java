package dnd.client;

import javax.swing.*;
import java.awt.*;

public class ChatGui extends JFrame implements Runnable {
    private JFrame frame;
    Client client;

    public ChatGui(Client client) {
        this.client = client;
    }

    private void chatContent(){
        frame=new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel chatPnl =new JPanel();
        frame.add(chatPnl);
        chatPnl.add(new JLabel("sono una chattt!!!!!!"));


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
