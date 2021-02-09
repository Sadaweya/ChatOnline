package dnd.client;

import dnd.server.ChatRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

class ChatRoomsGui extends JFrame implements Runnable {
    private JFrame frame;
    private final Client client;

    public ChatRoomsGui(Client client){
        this.client=client;
    }


    private void chatRoomsContent(){
        frame=new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                client.closeClient();
            }
        });
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
            btn.setName(chatRoom.getChatName());
            chatRoomListPnl.add(btn);
            btn.addActionListener(e -> {
                client.joinChatroom(btn.getName());//ritorna null e non il nome della chat
                ChatGui chatGui=new ChatGui(client,btn.getName());
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
