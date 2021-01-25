package dnd.client;

import dnd.client.core.BaseModel;
import dnd.server.ChatRoom;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.BaseStream;

class Client extends BaseModel {
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

    public String joinChatroom(String chatRoomName){
        try {
            System.out.println("sono quiiiii (client.joinChatRoom)");
            //inputStream = new ObjectInputStream(server.getInputStream());


            outputStream.writeUTF("SYSTEM_MESSAGE_JOIN_CHATROOM");
            outputStream.flush();
            System.out.println(chatRoomName);
            outputStream.writeUTF(chatRoomName);
            outputStream.flush();
            Thread updateChat=new Thread();
            return inputStream.readUTF();//restituisco il contenuto della chat
            //out.println("SYSTEM_MESSAGE_JOIN_CHATROOM");
            //out.println(chatRoomName);
            // System.out.println(" (client.getlistachats), ho passato il out.write");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void sendMsg(String chatRoomName,String clientName, String msg){
        try {
            System.out.println("sono quiiiii (client.sendMsg)");

            outputStream.writeUTF("SYSTEM_MESSAGE_SND_MESSAGE");
            outputStream.flush();

            outputStream.writeUTF(chatRoomName);
            outputStream.flush();

            outputStream.writeUTF(clientName);
            outputStream.flush();

            outputStream.writeUTF(msg);
            outputStream.flush();

            getChatContents(chatRoomName);
            fireValuesChange(new ChangeEvent(this));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getChatContents(String chatRoomName){
        try {
            System.out.println("sono quiiiii (client.getChatContent)");

            outputStream.writeUTF("SYSTEM_MESSAGE_GET_CHAT_CONTENTS");
            outputStream.flush();
            //System.out.println(chatRoomName);
            outputStream.writeUTF(chatRoomName);
            outputStream.flush();
            return inputStream.readUTF();
            //out.println("SYSTEM_MESSAGE_JOIN_CHATROOM");
            //out.println(chatRoomName);
            // System.out.println(" (client.getlistachats), ho passato il out.write");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
