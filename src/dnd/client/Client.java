package dnd.client;

import dnd.client.core.BaseModel;
import dnd.server.ChatRoom;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.BaseStream;

class Client extends BaseModel {
    private JFrame frame;
    private Socket server;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private static boolean permit;
    Semaphore semaphore;


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
        permit=false;
        semaphore = new Semaphore(1);
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
            permit = semaphore.tryAcquire(3, TimeUnit.SECONDS);
            if(permit){
                server = new Socket(ip,port);
                // System.out.println("mi sono connesso");
                ChatRoomsGui chatroomGui=new ChatRoomsGui(this);
                chatroomGui.run();
                closeGUI();
                outputStream = new ObjectOutputStream(server.getOutputStream());
                inputStream = new ObjectInputStream(server.getInputStream());
            }
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }finally {
            if(permit){
                semaphore.release();
                permit=false;
            }
        }
    }

    public ArrayList<ChatRoom> getListaChatRooms(){
        try{
            permit = semaphore.tryAcquire(3, TimeUnit.SECONDS);
            if(permit){
                // System.out.println("sono quiiiii (client.getlistachats)");
                outputStream.writeUTF("!SYSTEM_MESSAGE@GET_CHATROOMS_LIST");
                outputStream.flush();
                //outputStream.writeUTF("SECONDO_MESSAGGIO");
                //outputStream.flush();
                //out.println("SYSTEM_MESSAGE_GET_CHATROOMS_LIST");
                //System.out.println(" (client.getlistachats), ho passato il out.writeobject");
                return (ArrayList<ChatRoom>)inputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(permit){
                semaphore.release();
                permit=false;
            }
        }
        return null;
    }

    public void joinChatroom(String chatRoomName){
        try {
            permit = semaphore.tryAcquire(3, TimeUnit.SECONDS);
            if(permit){
                System.out.println("sono quiiiii (client.joinChatRoom)");
                //inputStream = new ObjectInputStream(server.getInputStream());


                outputStream.writeUTF("!SYSTEM_MESSAGE@JOIN_CHATROOM"
                        .concat("@")
                        .concat(chatRoomName));
                outputStream.flush();
                //System.out.println(chatRoomName);
                //outputStream.writeUTF(chatRoomName);
                //outputStream.flush();
                //return inputStream.readUTF();//restituisco il contenuto della chat
                //out.println("SYSTEM_MESSAGE_JOIN_CHATROOM");
                //out.println(chatRoomName);
                // System.out.println(" (client.getlistachats), ho passato il out.write");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(permit){
                semaphore.release();
                permit=false;
            }
        }
    }

    public void sendMsg(String chatRoomName,String clientName, String msg){
        try {
            permit = semaphore.tryAcquire(3, TimeUnit.SECONDS);
            if(permit){
                System.out.println("sono quiiiii (client.sendMsg)");

                outputStream.writeUTF("!SYSTEM_MESSAGE@SND_MESSAGE"
                        .concat("@")
                        .concat(chatRoomName)
                        .concat("@")
                        .concat(clientName)
                        .concat("@")
                        .concat(msg));
                outputStream.flush();

                //  outputStream.writeUTF(chatRoomName);
                //  outputStream.flush();

                // outputStream.writeUTF(clientName);
                // outputStream.flush();

                // outputStream.writeUTF(msg);
                // outputStream.flush();

                //getChatContents(chatRoomName);
                //  fireValuesChange(new ChangeEvent(this));
            }




        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(permit){
                semaphore.release();
                permit=false;
            }
        }
    }

    public String getChatContents(String chatRoomName){
        try {
            permit = semaphore.tryAcquire(3, TimeUnit.SECONDS);
            if(permit){
                System.out.println("sono quiiiii (client.getChatContent)");

                outputStream.writeUTF("!SYSTEM_MESSAGE@GET_CHAT_CONTENTS"
                        .concat("@")
                        .concat(chatRoomName));
                outputStream.flush();
                //System.out.println(chatRoomName);
                //outputStream.writeUTF(chatRoomName);
                // outputStream.flush();
                String temp=inputStream.readUTF();//PROBLEMA QUI ---->EOFEx
                System.out.println(temp);
                return temp;
                //out.println("SYSTEM_MESSAGE_JOIN_CHATROOM");
                //out.println(chatRoomName);
                // System.out.println(" (client.getlistachats), ho passato il out.write");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(permit){
                semaphore.release();
                permit=false;
            }
        }
        return null;
    }

    // devo sistemare la chiusura
    public void closeClient(){
        try {
            if(permit){
                outputStream.writeUTF("!SYSTEM_MESSAGE@QUIT");
                outputStream.flush();

                outputStream.close();
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(permit){
                semaphore.release();
                permit=false;
            }
        }
    }

    private void closeGUI(){
        frame.dispose();
    }

}
