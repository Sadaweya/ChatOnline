package dnd.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class CommProtocol implements Runnable {

    //private static ArrayList<CommProtocol> clientList=new ArrayList<>();
    Socket client;
    //ObjectInputStream inputStream;
    //ObjectOutputStream outputStream;




    public CommProtocol(Socket client) {
        this.client = client;

       // clientList.add(this);
    }

    @Override
    public void run() {
        try (
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                ){

            // protocollo di comunicazione
            String request;
            while(true) {
                System.out.println("inizio while");

                request = inputStream.readUTF();
                //System.out.println(inputStream.readUTF());

                System.out.printf("Richiesta ricevuta: %s\n", request);

                if("quit".equals(request)) {
                    System.out.printf("\nRichiesta di uscire dal servziio...\n");
                    break;
                }

                else if("SYSTEM_MESSAGE_GET_CHATROOMS_LIST".equals(request)){
                    System.out.println("messaggio di sistema ricevuto, invio lista chatrooms");
                    outputStream.writeObject(Server.getListaChats());
                    outputStream.flush();
                }

                else if("SYSTEM_MESSAGE_JOIN_CHATROOM".equals(request)){
                    String temp=inputStream.readUTF();
                    System.out.println("messaggio di sistema ricevuto, joina chat "+temp);
                    Server.joinChatRoom(temp,client);
                    outputStream.writeObject(Server.getListaChats());
                    outputStream.flush();
                }

                else if("SYSTEM_MESSAGE_GET_CHATHISTORY".equals(request)){
                    System.out.println("messaggio di sistema ricevuto, invio chat");

                    //outputStream.writeObject(Server.);  devo inviare la chat della chatroom selezionata
                    //devo ricevere la chatroom interessata
                }
                System.out.println("fine while");
              //  System.out.println(client);
               // System.out.println("prima "+request);
               // request = (String)inputStream.readObject();//perchè request diventa null?
               //  System.out.println("seconda "+request);
                 //break;

            }

            System.out.println("esco da while");




        } catch(IOException ex) {
            System.out.println("exception in commprotocol "+ex);

            ex.printStackTrace();
        }

        System.out.printf("Sessione terminata, client: %s\n",client);
    }

    public enum SystemMessages{
        SYSTEM_MESSAGE_GET_CHATROOMS_LIST,
        SYSTEM_MESSAGE_JOIN_CHATROOM,
        SYSTEM_MESSAGE_GET_CHATHISTORY;

    }


}
