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
                    String chatroomName=inputStream.readUTF();
                    System.out.println("messaggio di sistema ricevuto, joina chat "+chatroomName);
                    Server.joinChatRoom(chatroomName,client);
                    outputStream.writeUTF(
                            Server.getChatroom(chatroomName).chatHistory.toString()
                    );
                    outputStream.flush();

                }

                else if("SYSTEM_MESSAGE_SND_MESSAGE".equals(request)){
                    System.out.println("messaggio di sistema ricevuto, send message ");
                    String chatRoomName=inputStream.readUTF();
                    String clientName=inputStream.readUTF();
                    String msg=inputStream.readUTF();
                    System.out.printf("[%s][%s]: %s\n",chatRoomName,clientName,msg);
                    Server.getChatroom(chatRoomName).sndMsg(clientName,msg);
                    //updateChatroom(chatRoomName);
                }

                else if("SYSTEM_MESSAGE_GET_CHAT_CONTENTS".equals(request)){
                    System.out.println("messaggio di sistema ricevuto, invio chat");
                    String chatRoomName=inputStream.readUTF();
                    outputStream.writeObject(Server.getChatroom(chatRoomName).chatHistory);//devo inviare la chat della chatroom selezionata
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

    private void updateChatroom(String chatRoomName){
        //Server.getChatroom(chatRoomName).getListaPartecipanti().forEach();
        //ricevo la lista dei socket connessi alla chat, devo inviare a ognuno di loro l'evento
    }

    public enum SystemMessages{
        SYSTEM_MESSAGE_GET_CHATROOMS_LIST,
        SYSTEM_MESSAGE_JOIN_CHATROOM,
        SYSTEM_MESSAGE_GET_CHAT_CONTENTS;

    }


}
