package dnd.client;

import dnd.client.Client;

import javax.swing.*;
import java.awt.*;

public class ConnectGui extends JPanel {

    public ConnectGui(Client client){

        Dimension window=this.getSize();

        JLabel ipLbl=new JLabel("ip: ");
        JLabel portLbl=new JLabel("port: ");
        JTextField ipTxt=new JTextField("127.0.0.1");
        JTextField portTxt=new JTextField("1234");
        JButton connect=new JButton("CONNECT");
        JPanel pnlSx=new JPanel();
        pnlSx.setLayout(new BorderLayout());
        pnlSx.add(ipLbl,BorderLayout.NORTH);
        pnlSx.add(portLbl,BorderLayout.CENTER);
        JPanel pnlDx=new JPanel();
        pnlDx.setLayout(new BorderLayout());
        pnlDx.add(ipTxt,BorderLayout.NORTH);
        pnlDx.add(portTxt,BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        this.add(pnlSx,BorderLayout.WEST);
        this.add(pnlDx,BorderLayout.CENTER);
        this.add(connect,BorderLayout.SOUTH);

        connect.addActionListener(e ->client.connect(ipTxt.getText(),Integer.parseInt(portTxt.getText())));
    }
}
