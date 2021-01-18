package dnd;

import java.util.ArrayList;

public class ChatRoom {
    ArrayList<CommProtocol> listaPartecipanti=new ArrayList<>();
    String chatName;

    public ChatRoom(String chatName) {
        this.chatName = chatName;
    }

    //da void to boolean per ritornare se successo o meno
    public void addPartecipante(CommProtocol partecipante){
        listaPartecipanti.add(partecipante);
    }
    public void removePartecipante(CommProtocol partecipante) {
        listaPartecipanti.remove(partecipante);
    }
    public ArrayList<CommProtocol> getListaPartecipanti(){
        return listaPartecipanti;
    }



}
