package dnd.client;

import dnd.server.ChatRoom;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private JFrame frame;
    private Socket server;


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
            close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<ChatRoom> getListaChatRooms(){
        try (
                //ObjectOutputStream outputStream = new ObjectOutputStream(server.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(server.getInputStream());
                //BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter out = new PrintWriter(server.getOutputStream(), true);

        ) {

           // System.out.println("sono quiiiii (client.getlistachats)");
            out.println("SYSTEM_MESSAGE_GET_CHATROOMS_LIST");
           // System.out.println(" (client.getlistachats), ho passato il out.write");

            return (ArrayList<ChatRoom>)inputStream.readObject();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    private void close(){
        frame.dispose();
    }

}
