package dnd.server;

import java.io.*;
import java.net.Socket;
import java.util.*;

class CommProtocol implements Runnable {

    //private static ArrayList<CommProtocol> clientList=new ArrayList<>();
    Socket client;
    HashMap<String,HashMap<String,Exec>> commands;
    HashMap<String,Exec> systemCommands;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    private boolean run;

    //ObjectInputStream inputStream;
    //ObjectOutputStream outputStream;




    public CommProtocol(Socket client) {
        this.client = client;
        commands=new HashMap<>();
        systemCommands= new HashMap<>();
        run=true;

        try {
            inputStream = new ObjectInputStream(client.getInputStream());
            outputStream = new ObjectOutputStream(client.getOutputStream());

            systemCommands.put("GET_CHATROOMS_LIST",(s -> {
                System.out.println(s);
                System.out.println("messaggio di sistema ricevuto, invio lista chatrooms");
                outputStream.writeObject(Server.getListaChats());
                outputStream.flush();
            }));
            systemCommands.put("JOIN_CHATROOM",(chatroomName -> {
                System.out.println("messaggio di sistema ricevuto, joina chat "+ chatroomName);
                Server.joinChatRoom(chatroomName.peek(),client);
                outputStream.writeUTF(
                        Server.getChatroom(chatroomName.poll()).chatHistory.toString()
                );
                outputStream.flush();
            }));
            systemCommands.put("SND_MESSAGE",(attributes->{
                System.out.println("messaggio di sistema ricevuto, send message ");
                String chatRoomName=attributes.poll();
                String clientName=attributes.poll();
                String msg=attributes.poll();
                System.out.printf("[%s][%s]: %s\n",chatRoomName,clientName,msg);
                Server.getChatroom(chatRoomName).sndMsg(clientName,msg);
                    }));
            systemCommands.put("GET_CHAT_CONTENTS",(chatRoomName -> {
                System.out.println("messaggio di sistema ricevuto, invio chat");
                String temp=Server.getChatroom(chatRoomName.poll()).chatHistory.toString();
                System.out.println(temp);
                outputStream.writeObject(temp);//devo inviare la chat della chatroom selezionata
                outputStream.flush();
            }));
            systemCommands.put("QUIT",(s -> {
                System.out.println("messaggio di sistema ricevuto, quit");
                close();
            }));

            commands.put("!SYSTEM_MESSAGE", systemCommands);

        } catch (IOException e) {
            System.out.println("exception in commprotocol systemcommands"+e);

            e.printStackTrace();
        }


       // clientList.add(this);
    }

    @Override
    public void run() {
        try {

            // protocollo di comunicazione
            String request;
            while(run) {
                synchronized (this){
                    System.out.println("inizio while");
                    request = inputStream.readUTF();

                    //System.out.println(i nputStream.readUTF());

                    System.out.printf("Richiesta ricevuta: %s\n", request);

                    LinkedList<String> istruzioni = new LinkedList<>(Arrays.asList(request.split("@")));

                    commands.get(istruzioni.poll())
                            .get(istruzioni.poll())
                            .exec(istruzioni);


                    //importante concorrenza
                    //i messaggi successivi sovrascrivono i precedenti?

                    System.out.println("fine while");
                    //  System.out.println(client);
                    // System.out.println("prima "+request);
                    // request = (String)inputStream.readObject();//perchè request diventa null?
                    //  System.out.println("seconda "+request);
                    //break;

                }
                //System.out.println("esco da while");
                }





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

    private void close(){
        try {
            run=false;
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public enum SystemMessages{
        SYSTEM_MESSAGE_GET_CHATROOMS_LIST,
        SYSTEM_MESSAGE_JOIN_CHATROOM,
        SYSTEM_MESSAGE_GET_CHAT_CONTENTS;

    }


}
