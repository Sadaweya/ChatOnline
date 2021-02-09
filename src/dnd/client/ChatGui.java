package dnd.client;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ChatGui extends JFrame implements Runnable {
    private JFrame frame;
    Client client;
    String clientName;
    String chatName;

    public ChatGui(Client client, String chatName) {
        this.client = client;
        this.chatName = chatName;
        this.clientName = "Client "+ Math.random();
    }

    private void chatContent(){
        frame=new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setTitle(chatName);//dò il titolo alla finestra

        JPanel chatPnl =new JPanel();
        frame.add(chatPnl);
        chatPnl.setLayout(new BorderLayout());
        ChatHistoryPanel chatPnlOut=new ChatHistoryPanel(client,chatName);
        JPanel chatPnlIn=new JPanel();
        chatPnl.add(chatPnlIn,BorderLayout.SOUTH);
        chatPnl.add(chatPnlOut,BorderLayout.CENTER);
        JTextField chatAreaIn= new JTextField();
        chatAreaIn.setPreferredSize(new Dimension(frame.getWidth()*7/10,frame.getHeight()/10));
       // chatAreaOut.setPreferredSize(new Dimension(frame.getWidth()*9/10,frame.getHeight()*9/10));

        chatPnlIn.add(chatAreaIn,BorderLayout.LINE_START);
        JButton inviaBtn = new JButton("SEND");
        inviaBtn.setPreferredSize(new Dimension(frame.getWidth()*2/10,frame.getHeight()/10));
        chatPnlIn.add(inviaBtn,BorderLayout.LINE_END);


/*
        ScheduledExecutorService executorService= Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(() ->
                chatAreaOut.setText(client.getChatContents(chatName)),
                0,5, TimeUnit.SECONDS);

 */
        //IMPORTANTE, QUESTO MEDOTO INTERFERISCE CON GLI ALTRI, DEVO GESTIRE L'ACCESSO SINCRONIZZATO E
        //INSCINDIBILE ALL'IMPUT STREAM

        //DEVO UCCIDERE IL PROCESSO QUANDO CHIUDO LA CHAT
        inviaBtn.addActionListener(e -> {
            String in=chatAreaIn.getText();
            if(in!=null){
                client.sendMsg(chatName,clientName,in);
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
