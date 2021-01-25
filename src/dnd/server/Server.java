package dnd.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server {

    static ArrayList<CommProtocol> listaConnessi=new ArrayList<>();
    static ArrayList<ChatRoom> listaChats=new ArrayList<>();
    static int id = 1;


    public static void main(String[] args) {

        try (
                ServerSocket server =new ServerSocket(1234);
        ){
            createChatRoom("ChatRoom "+id++);
            createChatRoom("ChatRoom "+id++);
            createChatRoom("ChatRoom "+id++);

            while(true){
                System.out.println("in attesa di un client");
                Socket client=server.accept();
                System.out.println("connesso un client");
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

    public static void joinChatRoom(ChatRoom chatRoom, Socket client){
        System.out.println("server.joinchatroom");
        chatRoom.addPartecipante(client);
    }

    //ritornare boolean success
    public static void joinChatRoom(String chatRoom, Socket client){
        ChatRoom chatRoomObj=getChatroom(chatRoom);
        if(chatRoomObj!=null)
            joinChatRoom(chatRoomObj,client);
    }

    public static ChatRoom getChatroom(String chatRoomName){
        for(ChatRoom chatRoom: listaChats){
            if(chatRoom.chatName.equals(chatRoomName))
                return chatRoom;
        }
        return null;
    }

    public static ArrayList<ChatRoom> getListaChats(){
       // System.out.printf("Stò inviando %d chats\n", listaChats.size());
        return listaChats;
    }




}