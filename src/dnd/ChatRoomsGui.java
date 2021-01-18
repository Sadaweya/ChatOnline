package dnd;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class ChatRoomsGui extends JFrame implements Runnable {
    private JFrame frame;

    private void chatRoomsContent(){
        frame=new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        JPanel chatRoomList =new JPanel();
        chatRoomList.setLayout(new GridLayout());
        frame.add(chatRoomList);

        System.out.println("devo aggiungere la lista delle chats: "+Server.getListaChats().size());

        Server.listaChats.forEach(chatRoom -> {
            System.out.printf("adding %s\n",chatRoom.chatName);
            JButton btn=new JButton(chatRoom.chatName);
            chatRoomList.add(btn);

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
