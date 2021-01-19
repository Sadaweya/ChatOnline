package dnd.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class CommProtocol implements Runnable {

    private static ArrayList<CommProtocol> clientList=new ArrayList<>();
    Socket client;
    private PrintWriter out;



    public CommProtocol(Socket client) {
        this.client = client;
        clientList.add(this);
    }

    @Override
    public void run() {
        try(
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                //ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {

            out = new PrintWriter(client.getOutputStream(), true);

            // protocollo di comunicazione
            String request;
            while((request = in.readLine()) != null) {
                System.out.printf("\nRichiesta ricevuta: %s", request);

                if("quit".equals(request)) {
                    System.out.printf("\nRichiesta di uscire dal servziio...\n");
                    break;
                }

                if("SYSTEM_MESSAGE_GET_CHATROOMS_LIST".equals(request)){
                    System.out.println("messaggio di sistema ricevuto, invio lista chats");
                    outputStream.writeObject(Server.getListaChats());
                }

                String response = request; //request.toUpperCase();
            }


        } catch(IOException ex) {

        } finally {
            this.close();
        }

        System.out.printf("\nSessione terminata, client: \n");
    }

    public enum SystemMessages{
        SYSTEM_MESSAGE_GET_CHATROOMS_LIST;
    }

    public void close() {
        if(out != null)
            out.close();

        clientList.remove(this);
    }
}
