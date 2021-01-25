package dnd.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoom implements Serializable {
    ArrayList<Socket> listaPartecipanti=new ArrayList<>();
    String chatName;
    StringBuilder chatHistory;

    public ChatRoom(String chatName) {
        this.chatName = chatName;
        chatHistory= new StringBuilder();
        chatHistory.append("this chat is not cripted yet");
    }

    //da void to boolean per ritornare se successo o meno
    public void addPartecipante(Socket partecipante){
        System.out.println("chatroom.addpartecipante");
        listaPartecipanti.add(partecipante);
        listaPartecipanti.forEach(System.out::println);
    }

    public void sndMsg(String utente, String body){
        chatHistory.append(String.format("\n[%s]: %s",utente,body));
        System.out.println("\n\nchathistory: "+chatHistory);
        //updateChatPartecipanti();
       // CommProtocol.update(this.chatName);
    }

    //metodo errato, chiude la connessione e richiede un secondo socket solo per questo
    public void updateChatPartecipanti(){
        for(Socket client: listaPartecipanti) {
            try (
                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ) {
                out.writeUTF(chatHistory.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getChatContent(){
        return getChatContent("");
    }

    public String getChatContent(String s){
        chatHistory.append(s);
        return chatHistory.toString();
    }

    public void removePartecipante(Socket partecipante) {
        listaPartecipanti.remove(partecipante);
    }
    public ArrayList<Socket> getListaPartecipanti(){
        return listaPartecipanti;
    }
    public String getChatName(){
        return this.chatName;
    }

}
