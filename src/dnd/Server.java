package dnd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    static ArrayList<CommProtocol> listaConnessi=new ArrayList<>();
    static ArrayList<ChatRoom> listaChats=new ArrayList<>();
    static int id = 1;


    public static void main(String[] args) {

        try(
                ServerSocket server =new ServerSocket(1234);
        ) {
            createChatRoom("ChatRoom "+id++);
            createChatRoom("ChatRoom "+id++);
            createChatRoom("ChatRoom "+id++);

            while(true){
                Socket client=server.accept();
                System.out.println("connesso un client\n");
                CommProtocol protocol=new CommProtocol(client);
                listaConnessi.add(protocol);
                protocol.run();
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }


    public static void createChatRoom(String chatRoomName){
        //look for chats with that name, if no name is chosen generate one
        listaChats.add(new ChatRoom(chatRoomName));
    }

    public static void joinChatRoom(ChatRoom chatRoom, CommProtocol client){
        chatRoom.addPartecipante(client);
    }

    public static ArrayList<ChatRoom> getListaChats(){
        System.out.printf("Stò inviando %d chats\n", listaChats.size());
        return listaChats;
    }




}