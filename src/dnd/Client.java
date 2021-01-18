package dnd;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private JFrame frame;

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

        ClientGui clientGui=new ClientGui(this);
        frame.add(clientGui);
    }




    public void connect(String ip,int port){
        System.out.printf("ip: %s\nport: %d\n",ip,port);
        try (
                Socket server=new Socket(ip,port);
                ){
            System.out.println("mi sono connesso");
            ChatRoomsGui chatGui=new ChatRoomsGui();
            chatGui.run();
            close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void close(){
        frame.dispose();
    }

}
