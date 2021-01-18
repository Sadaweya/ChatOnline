package dnd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<CommProtocol> listaConnessi=new ArrayList<>();
    static ArrayList<ChatRoom> listaChats=new ArrayList<>();


    public static void main(String[] args) {

        try(
                ServerSocket server =new ServerSocket(1234);
        ) {
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


    public void createChatRoom(String chatRoomName){
        //look for chats with that name, if no name is chosen generate one
        listaChats.add(new ChatRoom(chatRoomName));
    }

    public void joinChatRoom(ChatRoom chatRoom, CommProtocol client){
        chatRoom.addPartecipante(client);
    }



}