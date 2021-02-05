package dnd.client;

import javax.swing.*;
import java.awt.*;

public class ChatHistoryPanel extends JPanel {
    Timer tm=new Timer(5000,e->updateChat());
    private Client client;
    private String chatHistory;
    private String chatName;

    public ChatHistoryPanel(Client client,String chatName ){
        this.client=client;
        this.chatName=chatName;
        updateChat();
        tm.start();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        JLabel chatAreaOut= new JLabel();
        this.add(chatAreaOut);
        chatAreaOut.setText(chatHistory);
    }

    private void updateChat(){
        chatHistory=client.getChatContents(chatName);
    }
}
