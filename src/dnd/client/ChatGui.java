package dnd.client;

import javax.swing.*;
import java.awt.*;

class ChatGui extends JFrame implements Runnable {
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
        chatPnl.setLayout(new BorderLayout());
        JPanel chatPnlOut=new JPanel();
        JPanel chatPnlIn=new JPanel();
        chatPnl.add(chatPnlIn,BorderLayout.SOUTH);
        chatPnl.add(chatPnlOut,BorderLayout.CENTER);
        JLabel chatAreaOut= new JLabel();
        JTextField chatAreaIn= new JTextField();
        chatAreaOut.setPreferredSize(new Dimension(frame.getWidth()*9/10,frame.getHeight()*9/10));
        chatAreaIn.setPreferredSize(new Dimension(frame.getWidth()*7/10,frame.getHeight()/10));
        chatPnlOut.add(chatAreaOut);
        chatPnlIn.add(chatAreaIn,BorderLayout.LINE_START);
        JButton inviaBtn = new JButton("SEND");
        inviaBtn.setPreferredSize(new Dimension(frame.getWidth()*2/10,frame.getHeight()/10));
        chatPnlIn.add(inviaBtn,BorderLayout.LINE_END);
        chatAreaOut.setVerticalTextPosition(0);
        


        inviaBtn.addActionListener(e -> {
            String in=chatAreaIn.getText();
            if(in!=null){
                chatAreaOut.setText(chatAreaOut.getText()+"\n"+in);
            }

        });



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
