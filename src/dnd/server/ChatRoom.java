package dnd.server;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoom implements Serializable {
    ArrayList<Socket> listaPartecipanti=new ArrayList<>();
    String chatName;
    StringBuilder chatHistory;

    public ChatRoom(String chatName) {
        this.chatName = chatName;
    }

    //da void to boolean per ritornare se successo o meno
    public void addPartecipante(Socket partecipante){
        System.out.println("chatroom.addpartecipante");
        listaPartecipanti.add(partecipante);
        listaPartecipanti.forEach(System.out::println);

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
