package dnd;

import java.net.Socket;
import java.util.ArrayList;

public class CommProtocol implements Runnable {

    private static ArrayList<CommProtocol> clientList=new ArrayList<>();
    Socket client;


    public CommProtocol(Socket client) {
        this.client = client;
        clientList.add(this);
    }

    @Override
    public void run() {

    }
}
