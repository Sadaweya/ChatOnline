package dnd.client;

import dnd.server.ChatRoom;
import dnd.server.Server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatRoomsGui extends JFrame implements Runnable {
    private JFrame frame;
    private Client client;

    public ChatRoomsGui(Client client){
        this.client=client;
    }


    private void chatRoomsContent(){
        frame=new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        JPanel chatRoomListPnl =new JPanel();
        chatRoomListPnl.setLayout(new GridLayout(0,1));
        frame.add(chatRoomListPnl);

        System.out.println("devo aggiungere la lista delle chats: ");

        ArrayList<ChatRoom> listaChats = client.getListaChatRooms();
        System.out.println("dimensione: "+listaChats.size());

        listaChats.forEach(chatRoom -> {
            System.out.printf("adding %s\n",chatRoom.getChatName());
            JButton btn=new JButton(chatRoom.getChatName());
            chatRoomListPnl.add(btn);
            btn.addActionListener(e -> {
                ChatGui chatGui=new ChatGui(client);
                chatGui.run();
            });
        });

    }


    @Override
    public void run() {
        EventQueue.invokeLater(() -> {
            try {
                chatRoomsContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void close(){
        frame.dispose();
    }

}
