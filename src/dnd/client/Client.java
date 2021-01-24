package dnd.client;

import dnd.server.ChatRoom;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class Client {
    private JFrame frame;
    private Socket server;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Client window = new Client();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public Client() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ConnectGui clientGui=new ConnectGui(this);
        frame.add(clientGui);
    }




    public void connect(String ip,int port){
       // System.out.printf("ip: %s\nport: %d\n",ip,port);
        try {
            server=new Socket(ip,port);
           // System.out.println("mi sono connesso");
            ChatRoomsGui chatroomGui=new ChatRoomsGui(this);
            chatroomGui.run();
            closeGUI();
            outputStream = new ObjectOutputStream(server.getOutputStream());
            inputStream = new ObjectInputStream(server.getInputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<ChatRoom> getListaChatRooms(){
        try{
            // System.out.println("sono quiiiii (client.getlistachats)");
            outputStream.writeUTF("SYSTEM_MESSAGE_GET_CHATROOMS_LIST");
            outputStream.flush();
            //outputStream.writeUTF("SECONDO_MESSAGGIO");
            //outputStream.flush();
            //out.println("SYSTEM_MESSAGE_GET_CHATROOMS_LIST");
            //System.out.println(" (client.getlistachats), ho passato il out.writeobject");
            return (ArrayList<ChatRoom>)inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void joinChatroom(String chatRoomName){
        try {
            System.out.println("sono quiiiii (client.joinChatRoom)");
            //inputStream = new ObjectInputStream(server.getInputStream());


            outputStream.writeUTF("SYSTEM_MESSAGE_JOIN_CHATROOM");
            outputStream.flush();
            System.out.println(chatRoomName);
            outputStream.writeUTF(chatRoomName);
            outputStream.flush();
            //out.println("SYSTEM_MESSAGE_JOIN_CHATROOM");
            //out.println(chatRoomName);
            // System.out.println(" (client.getlistachats), ho passato il out.write");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void closeClient(){
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeGUI(){
        frame.dispose();
    }

}
